function toggleSidebar() {
    var sidebar = document.getElementById('sidebar');
    var content = document.querySelector('.content');
    var toggle = document.querySelector('.sidebar-toggle');
    sidebar.classList.toggle('collapsed');
    content.classList.toggle('collapsed');
    toggle.style.left = sidebar.classList.contains('collapsed') ? '80px' : '260px';
}

function toggleForm(formId, username = '', displayName = '', email = '', address = '', mobile = '', others = '', image = '', age = '') {
    const addEmployeeForm = document.getElementById('addEmployeeForm');
    const editEmployeeForm = document.getElementById('editEmployeeForm');
    const viewEmployeeForm = document.getElementById('viewEmployeeDetailForm');

    [addEmployeeForm, editEmployeeForm, viewEmployeeForm].forEach(form => form.classList.remove('active'));

    switch (formId) {
        case 'add':
            addEmployeeForm.classList.add('active');
            break;
        case 'edit':
            editEmployeeForm.classList.add('active');
            document.getElementById('editUsername').value = username;
            document.getElementById('editDisplayName').value = displayName;
            document.getElementById('editEmail').value = email;
            document.getElementById('editAddress').value = address;
            document.getElementById('editMobile').value = mobile;
            document.getElementById('editOthers').value = others;
            
            document.getElementById('editAge').value = age;
            break;
        case 'view':
            viewEmployeeForm.classList.add('active');
            document.getElementById('viewUsername').value = username;
            document.getElementById('viewDisplayName').value = displayName;
            document.getElementById('viewEmail').value = email;
            document.getElementById('viewAddress').value = address;
            document.getElementById('viewMobile').value = mobile; 
            document.getElementById('viewOthers').value = others;
            
            document.getElementById('viewAge').value = age;
            break;
}
}

function toggleForm1(formId, serviceID = '', serviceName = '', description = '', price = '', time = '',image='') {
    const addServiceForm = document.getElementById('addServiceForm');
    const viewServiceForm = document.getElementById('viewServiceDetailForm');

    [addServiceForm, viewServiceForm].forEach(form => form.classList.remove('active'));

    switch (formId) {
        case 'addservice':
            addServiceForm.classList.add('active');
            break;     
        case 'viewservice':
            viewServiceForm.classList.add('active');
            document.getElementById('viewServiceID').value = serviceID;
            document.getElementById('viewServiceName').value = serviceName;
            document.getElementById('viewDescription').value = description;
            document.getElementById('viewPrice').value = price;
            document.getElementById('viewTime').value = time;
            document.getElementById('viewImage').src = image;
            break;
}
}

function closeForm(formId) {
    var form = document.getElementById(formId);
    form.classList.remove('active');
}

function redirectToProfile() {
    window.location.href = 'profile.jsp';
}

function redirectToLogout() {
    window.location.href = 'logout';
}

function submitViewEmployeeForm() {
    document.getElementById('viewEmployeeForm').submit();
}

function submitViewServiceForm() {
    document.getElementById('viewServiceForm').submit();
}

function submitViewRevenueForm() {
    document.getElementById('viewRevenueForm').submit();
}

function doDelete(username) {
    if (confirm("Are you sure to DELETE employee with username = " + username)) {
        window.location = "deleteEmployee?username=" + username;
    }
}

function doDeleteService(serviceName) {
    if (confirm("Are you sure to DELETE service with service name = " + serviceName)) {
        window.location = "deleteService?serviceName=" + serviceName;
    }
}

function enableEditing(formId) {
    const form = document.getElementById(formId);
    const inputs = form.querySelectorAll('input');

    inputs.forEach(input => input.removeAttribute('readonly'));

    const editButton = form.querySelector('button#editButton');
    const saveButton = form.querySelector('button#saveButton');

    if (editButton) {
        editButton.style.display = 'none';
    }
    if (saveButton) {
        saveButton.style.display = 'block';
    }

    // Enable editing for service detail form
    const editButtonService = form.querySelector('button#editButtonService');
    const saveButtonService = form.querySelector('button#saveButtonService');

    if (editButtonService) {
        editButtonService.style.display = 'none';
    }
    if (saveButtonService) {
        saveButtonService.style.display = 'block';
    }
}
function displayImage2(input) {
        var previewImage = document.getElementById("previewImage2");
        var file = input.files[0];
        var reader = new FileReader();

        reader.onload = function (e) {
            previewImage.src = e.target.result;
            previewImage.style.display = "block";
        }

        reader.readAsDataURL(file);
    }