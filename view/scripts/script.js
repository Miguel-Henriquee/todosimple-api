const taskEndPoint = "http://localhost:8080/task/user";

function hideLoader() {
    document.getElementById("loading").style.display = "none";
}

function show(tasks) {

    const tab = `
        <thead>
            <th scope="col">#</th>
            <th scope="col">Description</th>
        </thead>
    `;

   for (const task of tasks) {
        tab += `
            <tr>
                <td scope="row">${task.id}</td>
                <td scope="row">${task.description}</td>
            </t>
        `
   }

   document.getElementById("tasks").innerHTML = tab;
    
}

async function getTasks() {
    const key = "Authorization";
    
    const response = await fetch(taskEndPoint, { 
        method: "GET",
        headers: new Headers({
            Authorization: localStorage.getItem(key)
        }) 
    });

    const data = await response.json();
    if (response) hideLoader();
    show(data);
    
}

document.addEventListener("DOMContentLoaded", function(event) {
    if (!localStorage.getItem("Authorization"))
        window.location = "/view/login.html";
});

getTasks();