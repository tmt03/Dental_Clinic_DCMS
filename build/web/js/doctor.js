// Constants
const CONTEXT_PATH = '${contextPath}'; // Inject từ JSP
const SIDEBAR_COLLAPSED_WIDTH = '80px';
const SIDEBAR_EXPANDED_WIDTH = '260px';

// DOM Elements (cached)
const sidebar = document.getElementById('sidebar');
const content = document.querySelector('.content');
const toggle = document.querySelector('.sidebar-toggle');
const forms = {
    add: document.getElementById('addPatientForm'),
    edit: document.getElementById('editPatientForm'),
    view: document.getElementById('viewPatientDetailForm')
};

function toggleSidebar() {
    if (!sidebar) return;
    sidebar.classList.toggle('collapsed');
    if (content) content.classList.toggle('collapsed');
    if (toggle) toggle.style.left = sidebar.classList.contains('collapsed') ? SIDEBAR_COLLAPSED_WIDTH : SIDEBAR_EXPANDED_WIDTH;
}

function toggleForm(formId, username = '', displayName = '', email = '', address = '', mobile = '', others = '', image = '', age = '') {
    if (!forms[formId]) return;
    Object.values(forms).forEach(form => form?.classList.remove('active'));

    switch (formId) {
        case 'add':
            forms.add.classList.add('active');
            break;
        case 'edit':
            forms.edit.classList.add('active');
            forms.edit.querySelector('#editUsername').value = username;
            forms.edit.querySelector('#editDisplayName').value = displayName;
            forms.edit.querySelector('#editEmail').value = email;
            forms.edit.querySelector('#editAddress').value = address;
            forms.edit.querySelector('#editMobile').value = mobile;
            forms.edit.querySelector('#editOthers').value = others;
            forms.edit.querySelector('#editImage').value = image; // Sửa từ src thành value
            forms.edit.querySelector('#editAge').value = age;
            break;
        case 'view':
            forms.view.classList.add('active');
            forms.view.querySelector('#viewUsername').value = username;
            forms.view.querySelector('#viewDisplayName').value = displayName;
            forms.view.querySelector('#viewEmail').value = email;
            forms.view.querySelector('#viewAddress').value = address;
            forms.view.querySelector('#viewMobile').value = mobile;
            forms.view.querySelector('#viewOthers').value = others;
            forms.view.querySelector('#viewImage').value = image; // Sửa từ src thành value
            forms.view.querySelector('#viewAge').value = age;
            break;
    }
}

function closeForm(formId) {
    const form = document.getElementById(formId);
    if (form) form.classList.remove('active');
}

function redirectToProfile() {
    window.location.href = `${CONTEXT_PATH}/profile.jsp`;
}

function redirectToLogout() {
    if (confirm('Are you sure you want to logout?')) {
        window.location.href = `${CONTEXT_PATH}/core?action=LOGOUT`;
    }
}

function submitForm(formId) {
    const form = document.getElementById(formId);
    if (form) form.submit();
}

function submitViewPatientForm() { submitForm('viewPatientForm'); }
function submitViewMedicalAppointmentListForm() { submitForm('viewMedicalAppointmentForm'); }
function submitViewMedicalAppointmentListFormForNurse() { submitForm('viewMedicalAppointmentFormForNurse'); }
function submitViewMedicalAppointmentNeedConfirmListForm() { submitForm('viewMedicalAppointmentNeedConfirmForm'); }

function doViewResult(appointmentID) {
    window.location = `${CONTEXT_PATH}/viewresult?appointmentID=${encodeURIComponent(appointmentID)}`;
}

function showRejectModal(appointmentID) {
    const modal = document.getElementById('rejectModal');
    const input = document.getElementById('rejectAppointmentID');
    if (modal && input) {
        input.value = appointmentID;
        modal.style.display = 'block';
    }
}

function closeModal() {
    const modal = document.getElementById('rejectModal');
    if (modal) modal.style.display = 'none';
}