const apiRoot = 'http://localhost:8080/api/quicklinks';
const pages = document.querySelectorAll('.page');
const navButtons = document.querySelectorAll('.nav-button');
const form = document.getElementById('quicklink-form');
const messageBox = document.getElementById('home-message');
const quicklinksList = document.getElementById('quicklinks-list');
const manageList = document.getElementById('manage-list');
const auditTable = document.getElementById('audit-table');
const filterButton = document.getElementById('filter-button');
const filterEventType = document.getElementById('filter-event-type');
const filterQuicklinkName = document.getElementById('filter-quicklink-name');

const state = {
  quicklinks: [],
  audits: [],
};

function init() {
  navButtons.forEach((button) => {
    button.addEventListener('click', () => setPage(button.dataset.target));
  });

  form.addEventListener('submit', onCreateQuicklink);
  filterButton.addEventListener('click', renderAuditEvents);

  setPage('home');
  loadAllData();
}

function setPage(pageId) {
  pages.forEach((page) => page.classList.toggle('active', page.id === pageId));
  navButtons.forEach((button) => button.classList.toggle('active', button.dataset.target === pageId));
  if (pageId === 'console') {
    renderAuditEvents();
    renderManageList();
  }
}

async function loadAllData() {
  await Promise.all([fetchQuicklinks(), fetchAuditEvents()]);
  renderQuicklinks();
  renderAuditEvents();
  renderManageList();
}

async function fetchQuicklinks() {
  try {
    const response = await fetch(apiRoot);
    state.quicklinks = response.ok ? await response.json() : [];
  } catch (error) {
    state.quicklinks = [];
    console.error('Failed to load quicklinks', error);
  }
}

async function fetchAuditEvents() {
  try {
    const response = await fetch(`${apiRoot}/audit`);
    state.audits = response.ok ? await response.json() : [];
  } catch (error) {
    state.audits = [];
    console.error('Failed to load audit events', error);
  }
}

function renderQuicklinks() {
  if (!state.quicklinks.length) {
    quicklinksList.innerHTML = '<p>No quicklinks yet. Create one above.</p>';
    return;
  }

  quicklinksList.innerHTML = state.quicklinks
    .map((item) => {
      const tags = item.tags?.length ? item.tags.map((tag) => `<span class="tag">${escapeHtml(tag)}</span>`).join('') : '<span class="tag">no tags</span>';
      const status = item.blocked ? '<span class="error-note">Blocked</span>' : '<span class="success-note">Active</span>';
      return `
        <div class="quicklink-item">
          <div class="quicklink-heading">${escapeHtml(item.quicklink)} → <a href="${escapeHtml(item.targetUrl)}" target="_blank" rel="noreferrer">${escapeHtml(item.targetUrl)}</a></div>
          <div class="quicklink-meta">Created: ${formatDate(item.creationDate)} · Updated: ${formatDate(item.updateDate)} · Usage: ${item.usageCount} · Status: ${status}</div>
          <div class="tag-list">${tags}</div>
        </div>
      `;
    })
    .join('');
}

function renderManageList() {
  if (!state.quicklinks.length) {
    manageList.innerHTML = '<p>No quicklinks available to manage.</p>';
    return;
  }

  manageList.innerHTML = state.quicklinks
    .map((item) => {
      const blockedLabel = item.blocked ? 'Unblock' : 'Block';
      const buttonClass = item.blocked ? 'success-note' : 'block-button';
      return `
        <div class="manage-row">
          <h3>${escapeHtml(item.quicklink)}</h3>
          <div class="manage-meta">Target URL: ${escapeHtml(item.targetUrl)} · Status: ${item.blocked ? 'Blocked' : 'Active'}</div>
          <div class="action-row">
            <button class="danger-button" type="button" onclick="deleteQuicklink('${encodeURIComponent(item.quicklink)}','${escapeHtml(item.quicklink)}')">Delete</button>
            <button class="secondary-button ${buttonClass}" type="button" onclick="toggleBlock('${encodeURIComponent(item.quicklink)}', ${item.blocked})">${blockedLabel}</button>
          </div>
        </div>
      `;
    })
    .join('');
}

function renderAuditEvents() {
  const eventType = filterEventType.value;
  const quicklinkName = filterQuicklinkName.value.trim().toLowerCase();
  const filtered = state.audits.filter((event) => {
    const matchesType = eventType ? event.eventType === eventType : true;
    const matchesName = quicklinkName ? event.quicklink.toLowerCase().includes(quicklinkName) : true;
    return matchesType && matchesName;
  });

  if (!filtered.length) {
    auditTable.innerHTML = '<p>No audit events match the current filters.</p>';
    return;
  }

  auditTable.innerHTML = filtered
    .map((event) => `
      <div class="audit-row">
        <h3>${escapeHtml(event.eventType)} · ${escapeHtml(event.quicklink)}</h3>
        <div class="audit-meta">${formatDate(event.eventDate)} · ${escapeHtml(event.eventDesc)}</div>
      </div>
    `)
    .join('');
}

function setMessage(text, isError = false) {
  messageBox.textContent = text;
  messageBox.className = isError ? 'message error-note' : 'message success-note';
}

async function onCreateQuicklink(event) {
  event.preventDefault();
  setMessage('Creating quicklink...');

  const formData = new FormData(form);
  const quicklink = formData.get('quicklink').trim();
  const targetUrl = formData.get('targetUrl').trim();
  const tags = formData.get('tags').trim().split(',').map((tag) => tag.trim()).filter(Boolean);

  if (!quicklink || !targetUrl) {
    setMessage('Quicklink name and target URL are required.', true);
    return;
  }

  try {
    const response = await fetch(apiRoot, {
      method: 'POST',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify({ quicklink, targetUrl, tags }),
    });

    if (!response.ok) {
      const errorText = await response.text();
      throw new Error(errorText || 'Could not create quicklink.');
    }

    await fetchQuicklinks();
    renderQuicklinks();
    renderManageList();
    setMessage('Quicklink created successfully.');
    form.reset();
  } catch (error) {
    setMessage(`Create failed: ${error.message}`, true);
  }
}

async function deleteQuicklink(encodedName, displayName) {
  const quicklinkName = decodeURIComponent(encodedName);
  const confirmed = window.confirm(`Delete quicklink '${displayName}'?`);
  if (!confirmed) return;

  try {
    const response = await fetch(`${apiRoot}/${quicklinkName}`, { method: 'DELETE' });
    if (!response.ok) {
      throw new Error('Delete request failed.');
    }
    await fetchQuicklinks();
    renderQuicklinks();
    renderManageList();
    await fetchAuditEvents();
    renderAuditEvents();
    setMessage(`Deleted '${displayName}'.`);
  } catch (error) {
    setMessage(`Delete failed: ${error.message}`, true);
  }
}

async function toggleBlock(encodedName, currentlyBlocked) {
  const quicklinkName = decodeURIComponent(encodedName);
  try {
    const response = await fetch(`${apiRoot}/${quicklinkName}`, {
      method: 'PUT',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify({ blocked: !currentlyBlocked }),
    });
    if (!response.ok) {
      throw new Error('Update request failed.');
    }
    await fetchQuicklinks();
    renderQuicklinks();
    renderManageList();
    await fetchAuditEvents();
    renderAuditEvents();
    const action = currentlyBlocked ? 'unblocked' : 'blocked';
    setMessage(`Quicklink '${quicklinkName}' ${action}.`);
  } catch (error) {
    setMessage(`Update failed: ${error.message}`, true);
  }
}

function formatDate(value) {
  try {
    return new Intl.DateTimeFormat('en-US', {
      dateStyle: 'medium',
      timeStyle: 'short',
    }).format(new Date(value));
  } catch {
    return value || 'Unknown';
  }
}

function escapeHtml(text) {
  return text
    .replace(/&/g, '&amp;')
    .replace(/</g, '&lt;')
    .replace(/>/g, '&gt;')
    .replace(/"/g, '&quot;')
    .replace(/'/g, '&#039;');
}

window.deleteQuicklink = deleteQuicklink;
window.toggleBlock = toggleBlock;

init();
