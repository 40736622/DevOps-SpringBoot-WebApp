/**
 * Gets JSON result from api endpoint.
 * @param area_query - Decides what api endpoint to target.
 * @param n - Top n results.
 * @param area_name - Continent or region area to specify.
 * @returns {Promise<any>}
 */
async function getTableData(area_query, n, area_name) {
    try {
        const base_api_url = "http://localhost:8080/api";
        let api_query_url = "";

        switch (area_query) {
            case "world":
                api_query_url = `/countries/world?n=${n}`;
                break;

            case "continent":
                api_query_url = `/countries/continent?continent=${area_name}&n=${n}`;
                break;

            case "region":
                api_query_url = `/countries/region?region=${area_name}&n=${n}`;
                break;

            case "languages":
                api_query_url = "/languages";
                break;
        }

        const response = await fetch(base_api_url + api_query_url, {
            credentials: "omit",
            method: 'GET',
            headers: { "Content-Type" : "application/json; charset=UTF-8"}
        });

        if (!response.ok) {
            throw new Error(`Response status: ${response.status}`);
        }

        return await response.json();
    } catch (e) {
        console.error(e.message);
    }
}

/**
 * Creates Header row for table.
 * @param headers - Names for each table head cell.
 * @returns {HTMLTableSectionElement}
 */
function createTableHeader(...headers) {
    const tableHeader = document.createElement("thead");
    const tableHeaderRow = document.createElement("tr");

    for (const header of headers) {
        const tableHeading = document.createElement("th");
        tableHeading.textContent = header;
        tableHeaderRow.appendChild(tableHeading);
    }

    tableHeader.appendChild(tableHeaderRow);
    return tableHeader;
}

/**
 * Creates table from JSON response.
 * @param json - JSON response with table data.
 * @param queryType - Formats table header differently depending on type (languages/world/continent/region).
 */
function createTable(json, queryType) {
    const section = document.querySelector("section");
    let dataTable = document.querySelector("table");

    if (dataTable) {
        section.removeChild(dataTable);
    }

    dataTable = document.createElement("table");
    let tableHeader;

    if (queryType === "languages") {
        tableHeader = createTableHeader("Languages", "Total Speakers", "World Percentage");
    } else {
        tableHeader = createTableHeader("Name", "Population", "Code", "Continent",
            "Region", "Capital");
    }

    dataTable.appendChild(tableHeader);

    const tableBody = document.createElement("tbody");

    for (const apiItem of json) {
        const tableRow = document.createElement("tr");
        tableBody.appendChild(tableRow);

        Object.keys(apiItem).forEach(key => {
            const tableData = document.createElement("td")
            let itemData = apiItem[key];

            if (itemData !== null) {
                if (key === "population" || key === "totalSpeakers") {
                    itemData = itemData.toLocaleString();
                } else if (key === "worldPercentage") {
                    itemData = `${itemData}%`;
                }

                tableData.textContent = itemData
            } else {
                tableData.textContent = "N/A";
            }

            tableRow.appendChild(tableData);
        });
    }

    dataTable.appendChild(tableBody);
    section.appendChild(dataTable);
}

// DOM Elements
const queryForm = document.getElementById("query-form");
const apiSelect = document.getElementById("api-select");
const areaName = document.getElementById("area-name");
const topCount = document.getElementById("top-count");

if (apiSelect.value === "world") areaName.setAttribute("disabled", "");

getTableData("world", "", "").then(json => {
    if (json) createTable(json, "world");   // Create default table on page load.
});

queryForm.addEventListener("submit", (e) => {
    e.preventDefault();

    const apiSelectValue = apiSelect.value.toLowerCase();
    const areaNameValue = areaName.value.toLowerCase();
    const topCountValue = topCount.value.toLowerCase();

    getTableData(apiSelectValue, topCountValue, areaNameValue).then(json => {
        if (json) createTable(json, apiSelectValue); // Create new table depending on submitted form.
    });
});

// Disable/Enable inputs depending on value selected for dropdown.
apiSelect.addEventListener("change", (e) => {
    const apiSelectValue = apiSelect.options[apiSelect.selectedIndex].value;

    if (apiSelectValue === "world") {
        if (!areaName.hasAttribute("disabled")) areaName.setAttribute("disabled", "");
        if (topCount.hasAttribute("disabled")) topCount.removeAttribute("disabled");

    } else if (apiSelectValue === "languages") {
        if (!areaName.hasAttribute("disabled")) areaName.setAttribute("disabled", "");
        if (!topCount.hasAttribute("disabled")) topCount.setAttribute("disabled", "");
    } else {
        if (areaName.hasAttribute("disabled")) areaName.removeAttribute("disabled");
        if (topCount.hasAttribute("disabled")) topCount.removeAttribute("disabled");
    }
});