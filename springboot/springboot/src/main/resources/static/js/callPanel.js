import { stationLocations, map, geocoder } from "./mapSetup.js";
import { animateVehicleAlongRoute } from "./unitRouting.js";

/**
 * Displays a new form panel for users to input call details.
 */
export function showAddCallForm() {
  const callList = document.getElementById("callList");
  const panel = document.createElement("div");
  panel.className = "call-panel";

  panel.innerHTML = `
    <strong>Unit:</strong> <input type="text" class="unit" placeholder="Enter unit name (e.g., L1)" ><br>
    <strong>Type:</strong>
    <select class="unit-type">
      <option value="fire">Fire</option>
      <option value="police">Police</option>
      <option value="medical">Medical</option>
    </select><br>
    <strong>Status:</strong> <input type="text" class="status" placeholder="Enter emergency (e.g., House Fire)"><br>
    <strong>Address:</strong> <input type="text" class="address" placeholder="Enter Address (e.g., 123 Main St)"><br>
    <strong>Notes:</strong><br>
    <textarea class="notes"></textarea><br><br>

    <label><strong>Call Received:</strong></label>
    <input type="time" class="ts-received"><br>
    <label><strong>Dispatched:</strong></label>
    <input type="time" class="ts-dispatched"><br>
    <label><strong>On Scene:</strong></label>
    <input type="time" class="ts-onscene"><br>
    <label><strong>Transporting:</strong></label>
    <input type="time" class="ts-transport"><br>
    <label><strong>At Hospital:</strong></label>
    <input type="time" class="ts-arrived"><br>

    <button class="add-unit-btn">Add Unit</button>
  `;

  panel.querySelector(".add-unit-btn").addEventListener("click", () => addCall(panel));
  callList.prepend(panel);
}

/**
 * Collects call form data and retains the form inputs as editable fields.
 * @param {HTMLElement} panel - The call panel containing input fields.
 */
function addCall(panel) {
  const unit = panel.querySelector(".unit").value;
  const type = panel.querySelector(".unit-type").value;
  const status = panel.querySelector(".status").value;
  const address = panel.querySelector(".address").value;
  const notes = panel.querySelector(".notes").value;
  const received = panel.querySelector(".ts-received").value;
  const dispatched = panel.querySelector(".ts-dispatched").value;
  const onscene = panel.querySelector(".ts-onscene").value;
  const transport = panel.querySelector(".ts-transport").value;
  const arrived = panel.querySelector(".ts-arrived").value;

  let timestampInputs = `
    <label><strong>Call Received:</strong></label>
    <input type="time" class="ts-received" value="${received}"><br>
    <label><strong>Dispatched:</strong></label>
    <input type="time" class="ts-dispatched" value="${dispatched}"><br>
    <label><strong>On Scene:</strong></label>
    <input type="time" class="ts-onscene" value="${onscene}"><br>
  `;

  if (type === "police" || type === "medical") {
    timestampInputs += `
      <label><strong>Transporting:</strong></label>
      <input type="time" class="ts-transport" value="${transport}"><br>
    `;
  }
  if (type === "medical") {
    timestampInputs += `
      <label><strong>At Hospital:</strong></label>
      <input type="time" class="ts-arrived" value="${arrived}"><br>
    `;
  }

  panel.innerHTML = `
    <strong>Unit:</strong> <input type="text" class="unit" value="${unit}"><br>
    <strong>Type:</strong> <input type="text" class="unit-type" value="${type}" readonly><br>
    <strong>Status:</strong> <input type="text" class="status" value="${status}"><br>
    <strong>Address:</strong> <input type="text" class="address" value="${address}"><br>
    <strong>Notes:</strong><br>
    <textarea class="notes">${notes}</textarea><br>
    ${timestampInputs}
  `;

  geocoder.geocode({ address }, (results, status) => {
    if (status === "OK") {
      const destination = results[0].geometry.location;
      const origin = stationLocations[type];
      animateVehicleAlongRoute(unit, origin, destination, type);
    } else {
      alert("Geocode failed: " + status);
    }
  });
}
