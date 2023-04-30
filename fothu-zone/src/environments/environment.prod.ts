const ERROR_STATUS_CODES: Array<Number> = [400, 401, 402, 403, 404, 405, 500, 501, 502, 503, 504, 505];

export const environment = {
	production: true,
	errorCodes: ERROR_STATUS_CODES,
	fothuZoneEC2Link: "http://ec2-3-89-74-168.compute-1.amazonaws.com:7070",
	homeURL: "http://fothu.zone",
	fothuZoneWSLink: "ws://ec2-3-89-74-168.compute-1.amazonaws.com:7070",
};
