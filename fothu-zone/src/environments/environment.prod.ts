const ERROR_STATUS_CODES: Array<Number> = [400, 401, 402, 403, 404, 405, 500, 501, 502, 503, 504, 505];

export const environment = {
  production: true,
  errorCodes: ERROR_STATUS_CODES,
  fothuZoneEC2Link: "http://ec2-3-95-255-63.compute-1.amazonaws.com:6969",
};
