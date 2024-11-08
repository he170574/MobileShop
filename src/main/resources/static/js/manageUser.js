async function searchAccounts() {
    const name = document.getElementById("searchInput").value;
    const response = await fetch(`/api/admin/accounts/search?name=${name}`);
    const data = await response.json();
    displayAccounts(data);
}

async function filterByRole() {
    const role = document.getElementById("roleFilter").value;
    const response = await fetch(`/api/admin/accounts/filter?role=${role}`);
    const data = await response.json();
    displayAccounts(data);
}

async function updateAccount(id, accountData) {
    const response = await fetch(`/api/admin/accounts/${id}`, {
        method: "PUT",
        headers: {
            "Content-Type": "application/json",
        },
        body: JSON.stringify(accountData),
    });
    if (response.ok) {
        alert("Account updated successfully");
        loadAccounts(); // Refresh the list
    }
}

function displayAccounts(accounts) {
    const tbody = document.getElementById("userTable").getElementsByTagName("tbody")[0];
    tbody.innerHTML = "";

    accounts.forEach(account => {
        const row = tbody.insertRow();
        row.innerHTML = `
            <td>${account.accountId}</td>
            <td>${account.fullName}</td>
            <td>${account.email}</td>
            <td>${account.registerDate}</td>
            <td>${account.address}</td>
            <td>${account.role.roleName}</td>
            <td>
                <button onclick="editAccount(${account.accountId})">Edit</button>
                <button onclick="deleteAccount(${account.accountId})">Delete</button>
            </td>
        `;
    });
}
