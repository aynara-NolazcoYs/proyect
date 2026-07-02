const apiBase = 'http://localhost:3000/api';

const totalStudentsEl = document.getElementById('total-students');
const totalEnrollmentsEl = document.getElementById('total-enrollments');
const totalPromotersEl = document.getElementById('total-promoters');
const studentsPanel = document.getElementById('students-panel');
const promotersPanel = document.getElementById('promoters-panel');
const enrollmentPanel = document.getElementById('enrollment-panel');
const studentsTable = document.getElementById('students-table');
const promotersTable = document.getElementById('promoters-table');
const promotorSelect = document.getElementById('promotor');
const enrollmentForm = document.getElementById('enrollment-form');

const viewStudentsBtn = document.getElementById('view-students');
const viewPromotersBtn = document.getElementById('view-promoters');
const registerEnrollmentBtn = document.getElementById('register-enrollment');
const closeStudentsBtn = document.getElementById('close-students');
const closePromotersBtn = document.getElementById('close-promoters');
const closeEnrollmentBtn = document.getElementById('close-enrollment');
const cancelEnrollmentBtn = document.getElementById('cancel-enrollment');

async function fetchJson(path) {
  const res = await fetch(`${apiBase}${path}`);
  if (!res.ok) {
    throw new Error('Error al obtener datos');
  }
  return res.json();
}

async function loadDashboard() {
  const data = await fetchJson('/dashboard');
  totalStudentsEl.textContent = data.totalStudents;
  totalEnrollmentsEl.textContent = data.totalEnrollments;
  totalPromotersEl.textContent = data.totalPromoters;
}

async function loadStudents() {
  const students = await fetchJson('/students');
  studentsTable.innerHTML = students.map(student => `
    <tr>
      <td>${student.nombre}</td>
      <td>${student.apellido}</td>
      <td>${student.dni}</td>
      <td>${student.correo}</td>
    </tr>
  `).join('');
}

async function loadPromoters() {
  const promoters = await fetchJson('/promoters');
  promotersTable.innerHTML = promoters.map(promoter => `
    <tr>
      <td>${promoter.dni}</td>
      <td>${promoter.nombreCompleto}</td>
      <td>${promoter.sede}</td>
      <td>${promoter.email}</td>
    </tr>
  `).join('');
  promotorSelect.innerHTML = `
    <option value="">Selecciona un promotor</option>
    ${promoters.map(promoter => `<option value="${promoter.nombreCompleto}">${promoter.nombreCompleto} (${promoter.sede})</option>`).join('')}
  `;
}

function togglePanel(panel) {
  [studentsPanel, promotersPanel, enrollmentPanel].forEach(el => el.classList.add('hidden'));
  panel.classList.remove('hidden');
}

viewStudentsBtn.addEventListener('click', async () => {
  await loadStudents();
  togglePanel(studentsPanel);
});

viewPromotersBtn.addEventListener('click', async () => {
  await loadPromoters();
  togglePanel(promotersPanel);
});

registerEnrollmentBtn.addEventListener('click', async () => {
  await loadPromoters();
  togglePanel(enrollmentPanel);
});

closeStudentsBtn.addEventListener('click', () => studentsPanel.classList.add('hidden'));
closePromotersBtn.addEventListener('click', () => promotersPanel.classList.add('hidden'));
closeEnrollmentBtn.addEventListener('click', () => enrollmentPanel.classList.add('hidden'));
cancelEnrollmentBtn.addEventListener('click', () => enrollmentPanel.classList.add('hidden'));

enrollmentForm.addEventListener('submit', async event => {
  event.preventDefault();
  const formData = new FormData(enrollmentForm);
  const payload = Object.fromEntries(formData.entries());

  try {
    const created = await fetch(`${apiBase}/enrollments`, {
      method: 'POST',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify(payload)
    });

    if (!created.ok) {
      const error = await created.json();
      throw new Error(error.error || 'No se pudo guardar la matrícula');
    }

    enrollmentForm.reset();
    enrollmentPanel.classList.add('hidden');
    await loadDashboard();
    alert('Matrícula guardada correctamente.');
  } catch (err) {
    alert(err.message);
  }
});

window.addEventListener('load', async () => {
  await loadDashboard();
});
