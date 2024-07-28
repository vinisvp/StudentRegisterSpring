$("#inputTel").mask("(00) 00000-0000");

var courses = [];
var students = [];

var shift = ["Manhã", "Tarde", "Noite"];

function loadCourses(){
    $.ajax({
        url: "http://localhost:8080/courses",
        type: "GET",
        async: false,
        success: (response) => {
            courses = response;
            for(let course of courses){
                document.getElementById("inputCourse").innerHTML += `<option value=${course.id}>${course.name}</option>`;
            }
        }
    });
}

function addStudentToTable(student) {
    var table = document.getElementById("studentsTable");
    var newRow = table.insertRow();

    var Node = document.createTextNode(student.id);
    newRow.insertCell().appendChild(Node);

    Node = document.createTextNode(student.name);
    newRow.insertCell().appendChild(Node);

    Node = document.createTextNode(student.email);
    var cell = newRow.insertCell();
    cell.className = "d-none d-lg-table-cell";
    cell.appendChild(Node);

    Node = document.createTextNode(student.phone);
    cell = newRow.insertCell();
    cell.className = "d-none d-lg-table-cell";
    cell.appendChild(Node);

    Node = document.createTextNode(courses[student.idCourse - 1].name);
    cell = newRow.insertCell();
    cell.className = "d-none d-lg-table-cell";
    cell.appendChild(Node);

    Node = document.createTextNode(shift[student.period - 1]);
    cell = newRow.insertCell();
    cell.className = "d-none d-lg-table-cell";
    cell.appendChild(Node);
}

function loadStudents() {
    $.getJSON("http://localhost:8080/students", (response) => {
        students = response;
        for (let student of students) {
            addStudentToTable(student);
        }
    });
}

function addStudent() {
    var shifts = document.getElementsByName("inputShift");
    var sh = 0;
    for (s of shifts) {
        if (s.checked) {
            sh = parseInt(s.id);
        }
    }

    var student = {
        name: document.getElementById("inputName").value,
        email: document.getElementById("inputEmail").value,
        phone: document.getElementById("inputTel").value,
        idCourse: parseInt(document.getElementById("inputCourse").value),
        period: sh
    }

    $.ajax({
        url: "http://localhost:8080/students",
        type: "POST",
        contentType: "application/json",
        data: JSON.stringify(student),
        success: (respStudent) => {
            document.getElementById("formStudent").reset();
            students.push(respStudent);
            addStudentToTable(respStudent);
        }
    })
}

loadCourses();
loadStudents();
