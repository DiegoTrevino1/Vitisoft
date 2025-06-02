import { stationLocations, geocoder } from "./mapSetup.js";
import { animateVehicleAlongRoute } from "./unitRouting.js";

/**
 * Adds a new dynamic call panel and wires backend connection
 */
export function showAddCallForm() {
  const callList = document.getElementById("callList");
  const panel = document.createElement("div");
  panel.className = "call-panel";

  panel.innerHTML = `
    <strong>Unit:</strong> <input type="text" class="unit"><br>
    <strong>Type:</strong>
    <select class="unit-type">
      <option value="Fire">Fire</option>
      <option value="Police">Police</option>
      <option value="Medical">Medical</option>
    </select><br>
    <strong>Status:</strong> <input type="text" class="status"><br>
    <strong>Address:</strong> <input type="text" class="address"><br>
    <button class="submit-new-call">Add Call</button>
  `;

  callList.prepend(panel); // Add the panel to the page

  // 🔗 Wire the backend logic to this NEW panel's button
  const btn = panel.querySelector(".submit-new-call");
  btn.addEventListener("click", async () => {
    const unit = panel.querySelector(".unit").value;
    const type = panel.querySelector(".unit-type").value;
    const status = panel.querySelector(".status").value;
    const address = panel.querySelector(".address").value;

    const emergency = {
      userName: "dispatcher1",
      receivedTime: new Date().toISOString(),
      callerID: unit,
      details: status,
      address: address,
      type: type,
      isActive: true,
      priority: 5
    };

    console.log("Submitting from New Call Panel:", emergency);

    const res = await fetch("http://localhost:8080/api/emergency/add", {
      method: "POST",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify(emergency)
    });

    if (res.ok) {
      alert(" New emergency submitted!");
      geocodeAndAnimate(unit, address, type);
    } else {
      alert(" Failed to submit new call.");
    }
  });
}

// Animation logic
function geocodeAndAnimate(unit, address, type) {
  geocoder.geocode({ address }, (results, status) => {
    if (status === "OK") {
      const destination = results[0].geometry.location;
      const origin = stationLocations[type.toLowerCase()];
      animateVehicleAlongRoute(unit, origin, destination, type.toLowerCase());
    } else {
      alert("Geocode failed: " + status);
    }
  });
}
