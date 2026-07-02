const express = require('express');
const cors = require('cors');
const fs = require('fs');
const path = require('path');

const dataDir = path.join(__dirname, 'data');
const studentsPath = path.join(dataDir, 'students.json');
const promotersPath = path.join(dataDir, 'promoters.json');
const enrollmentsPath = path.join(dataDir, 'enrollments.json');

function readJson(filePath) {
  const content = fs.readFileSync(filePath, 'utf8');
  return JSON.parse(content);
}

function writeJson(filePath, data) {
  fs.writeFileSync(filePath, JSON.stringify(data, null, 2), 'utf8');
}

const app = express();
app.use(cors());
app.use(express.json());

app.get('/api/students', (req, res) => {
  const students = readJson(studentsPath);
  res.json(students);
});

app.get('/api/promoters', (req, res) => {
  const promoters = readJson(promotersPath);
  res.json(promoters);
});

app.get('/api/enrollments', (req, res) => {
  const enrollments = readJson(enrollmentsPath);
  res.json(enrollments);
});

app.post('/api/enrollments', (req, res) => {
  const enrollments = readJson(enrollmentsPath);
  const { nombre, apellido, dni, correo, carrera, sede, promotor } = req.body;

  if (!nombre || !apellido || !dni || !correo || !carrera || !sede || !promotor) {
    return res.status(400).json({ error: 'Todos los campos son obligatorios.' });
  }

  const newEnrollment = {
    id: enrollments.length + 1,
    nombre,
    apellido,
    dni,
    correo,
    carrera,
    sede,
    promotor,
    fecha: new Date().toISOString()
  };

  enrollments.push(newEnrollment);
  writeJson(enrollmentsPath, enrollments);
  res.status(201).json(newEnrollment);
});

app.get('/api/dashboard', (req, res) => {
  const students = readJson(studentsPath);
  const promoters = readJson(promotersPath);
  const enrollments = readJson(enrollmentsPath);

  res.json({
    totalStudents: students.length,
    totalPromoters: promoters.length,
    totalEnrollments: enrollments.length
  });
});

const port = process.env.PORT || 3000;
app.listen(port, () => {
  console.log(`EduFuturo backend running on http://localhost:${port}`);
});
