/**
 * Initializes the Google Map and configures base map settings.
 * Adds station markers for fire, police, and medical.
 */
export let map, geocoder, directionsService;

export const stationLocations = {
  fire: { lat: 46.98416, lng: -120.54248 },
  police: { lat: 46.99270, lng: -120.54675 },
  medical: { lat: 46.98754, lng: -120.53771 },
};

export function initMap() {
  console.log("initMap() called");
  map = new google.maps.Map(document.getElementById("map"), {
    center: { lat: 47.0, lng: -120.538 },
    zoom: 13,
    mapTypeControl: false,
  });

  geocoder = new google.maps.Geocoder();
  directionsService = new google.maps.DirectionsService();

  addStationMarker(stationLocations.fire, "Fire Department", "red");
  addStationMarker(stationLocations.police, "Police Station", "blue");
  addStationMarker(stationLocations.medical, "Hospital", "green");
}

/**
 * Adds a circular marker for a given emergency station.
 * @param {Object} position - Coordinates for the marker.
 * @param {string} title - Title shown on hover.
 * @param {string} color - Marker color.
 */
function addStationMarker(position, title, color) {
  new google.maps.Marker({
    position,
    map,
    title,
    icon: {
      path: google.maps.SymbolPath.CIRCLE,
      scale: 8,
      fillColor: color,
      fillOpacity: 1,
      strokeWeight: 1,
    },
  });
}