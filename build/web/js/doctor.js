function toggleSidebar() {
    var sidebar = document.getElementById('sidebar');
    var content = document.querySelector('.content');
    var toggle = document.querySelector('.sidebar-toggle');
    sidebar.classList.toggle('collapsed');
    content.classList.toggle('collapsed');
    toggle.style.left = sidebar.classList.contains('collapsed') ? '80px' : '260px';
}

function toggleForm(formId, username = '', displayName = '', email = '', address = '', mobile = '', others = '', image = '', age = '') {
    const addForm = document.getElementById('addPatientForm');
    const editForm = document.getElementById('editPatientForm');
    const viewForm = document.getElementById('viewPatientDetailForm');

    [addForm, editForm, viewForm].forEach(form => form.classList.remove('active'));

    switch (formId) {
        case 'add':
            addForm.classList.add('active');
            break;
        case 'edit':
            editForm.classList.add('active');
            document.getElementById('editUsername').value = username;
            document.getElementById('editDisplayName').value = displayName;
            document.getElementById('editEmail').value = email;
            document.getElementById('editAddress').value = address;
            document.getElementById('editMobile').value = mobile;
            document.getElementById('editOthers').value = others;
            document.getElementById('editImage').src = image;
            document.getElementById('editAge').value = age;
            break;
        case 'view':
            viewForm.classList.add('active');
            document.getElementById('viewUsername').value = username;
            document.getElementById('viewDisplayName').value = displayName;
            document.getElementById('viewEmail').value = email;
            document.getElementById('viewAddress').value = address;
            document.getElementById('viewMobile').value = mobile;
            document.getElementById('viewOthers').value = others;
            document.getElementById('viewImage').src = image;
            document.getElementById('viewAge').value = age;
            break;
}
}

function closeForm(formId) {
    document.getElementById(formId).classList.remove('active');
}

function redirectToProfile() {
    window.location.href = 'profile.jsp';
}

function redirectToLogout() {
    window.location.href = 'logout';
}

function submitViewPatientForm() {
    document.getElementById('viewPatientForm').submit();
}

function submitViewMedicalAppointmentListForm() {
    document.getElementById('viewMedicalAppointmentForm').submit();
}
function submitViewMedicalAppointmentListFormForNurse() {
    document.getElementById('viewMedicalAppointmentFormForNurse').submit();
}
function submitViewMedicalAppointmentHistoryListForm() {
    document.getElementById('viewMedicalAppointmentHistoryForm').submit();
}

function doViewResult(appointmentID) {
    window.location = "viewresult?appointmentID=" + appointmentID;
}
function doAddResult(appointmentID, date, service, doctor) {
    window.location = "saveresult?appointmentID=" + appointmentID + "&date=" + date + "&service=" + service + "&doctor=" + doctor;
}
