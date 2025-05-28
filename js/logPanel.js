/**
 * Shows the log history modal.
 */
export function showLogHistory() {
  const modal = document.getElementById("logHistoryModal");
  modal.style.display = "block";
}

/**
 * Hides the log history modal.
 */
export function hideLogHistory() {
  const modal = document.getElementById("logHistoryModal");
  modal.style.display = "none";
}

/**
 * Populates the log table with sample or stored data.
 * @param {Array} logs - Array of log objects to display.
 */
export function populateLogs(logs) {
  const tbody = document.querySelector("#logTable tbody");
  tbody.innerHTML = "";
  logs.forEach(log => {
    const row = document.createElement("tr");
    row.innerHTML = `
      <td>${log.date}</td>
      <td>${log.time}</td>
      <td>${log.type}</td>
      <td>${log.address}</td>
      <td>${log.unit}</td>
    `;
    tbody.appendChild(row);
  });
}
