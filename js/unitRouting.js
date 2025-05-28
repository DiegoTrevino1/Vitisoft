import { map, directionsService, stationLocations } from "./mapSetup.js";

/**
 * Animates a unit from its origin to the scene and optionally to the hospital.
 * Draws a polyline to represent the route on the map and moves the unit marker along the path.
 *
 * @param {string} id - Unique unit identifier displayed on the marker.
 * @param {Object} origin - Starting coordinates for the unit (station location).
 * @param {Object} destination - Destination coordinates of the incident scene.
 * @param {string} type - Unit type ('fire', 'police', or 'medical') used to determine styling and behavior.
 */
export function animateVehicleAlongRoute(id, origin, destination, type) {
  directionsService.route(
    { origin, destination, travelMode: google.maps.TravelMode.DRIVING },
    (result, status) => {
      if (status !== "OK") {
        alert("Directions request failed: " + status);
        return;
      }

      const path = result.routes[0].overview_path;
      const iconColor = type === "fire" ? "red" : type === "police" ? "blue" : "green";

      const polyline = new google.maps.Polyline({
        path,
        geodesic: true,
        strokeColor: iconColor,
        strokeOpacity: 0.7,
        strokeWeight: 4,
        map,
      });

      const marker = new google.maps.Marker({
        position: path[0],
        map,
        label: id,
        icon: {
          path: google.maps.SymbolPath.BACKWARD_CLOSED_ARROW,
          scale: 5,
          fillColor: iconColor,
          fillOpacity: 1,
          strokeWeight: 1,
        },
      });

      let index = 0;
      const onSceneIndex = Math.floor(path.length * 0.3);

      function moveToScene() {
        if (index === onSceneIndex) {
          console.log(`${id} on scene at ${new Date().toLocaleTimeString()}`);
        }
        if (index < path.length) {
          marker.setPosition(path[index]);
          index++;
          setTimeout(moveToScene, 750);
        } else if (type === "police" || type === "medical") {
          startTransport(id, destination, stationLocations.medical, marker, iconColor, type);
        }
      }

      moveToScene();
    }
  );
}

/**
 * Animates the return of a unit from the incident scene to the hospital if applicable.
 * A separate polyline is drawn to represent the transport route.
 *
 * @param {string} id - Unit identifier.
 * @param {Object} origin - Coordinates of the incident scene.
 * @param {Object} destination - Coordinates of the hospital.
 * @param {Object} marker - The marker object representing the unit.
 * @param {string} color - Color for the transport polyline.
 * @param {string} type - Unit type (used for conditionally logging arrival).
 */
function startTransport(id, origin, destination, marker, color, type) {
  directionsService.route(
    { origin, destination, travelMode: google.maps.TravelMode.DRIVING },
    (result, status) => {
      if (status !== "OK") return;

      const path = result.routes[0].overview_path;

      new google.maps.Polyline({
        path,
        geodesic: true,
        strokeColor: color,
        strokeOpacity: 0.7,
        strokeWeight: 4,
        map,
      });

      let index = 0;
      function moveToHospital() {
        if (index < path.length) {
          marker.setPosition(path[index]);
          index++;
          setTimeout(moveToHospital, 750);
        } else if (type === "medical") {
          console.log(`${id} arrived at hospital at ${new Date().toLocaleTimeString()}`);
        }
      }

      moveToHospital();
    }
  );
}
