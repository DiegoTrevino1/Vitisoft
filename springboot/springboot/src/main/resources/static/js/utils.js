/**
 * Returns the current time in HH:MM:SS format.
 * @returns {string} Formatted local time.
 */
export function getCurrentTimestamp() {
  return new Date().toLocaleTimeString();
}

/**
 * Pads a number with leading zeros if needed.
 * @param {number} number - The number to pad.
 * @param {number} length - Desired total length.
 * @returns {string} Zero-padded number as a string.
 */
export function padZero(number, length = 2) {
  return number.toString().padStart(length, "0");
}

/**
 * Formats a Date object to HH:MM string (24-hour).
 * @param {Date} date - The Date object.
 * @returns {string} Formatted time string.
 */
export function formatTimeHHMM(date = new Date()) {
  const hours = padZero(date.getHours());
  const minutes = padZero(date.getMinutes());
  return `${hours}:${minutes}`;
}

/**
 * Generates a unique ID for a new unit based on timestamp.
 * @returns {string} Unique string ID.
 */
export function generateUnitId() {
  return `U${Date.now().toString().slice(-5)}`;
}