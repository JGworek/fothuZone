// This file can be replaced during build by using the `fileReplacements` array.
// `ng build --prod` replaces `environment.ts` with `environment.prod.ts`.
// The list of file replacements can be found in `angular.json`.

const ERROR_STATUS_CODES: Array<Number> = [400, 401, 402, 403, 404, 405, 500, 501, 502, 503, 504, 505];

export const environment = {
  production: false,
  errorCodes: ERROR_STATUS_CODES,
  fothuZoneEC2Link: "http://localhost:6969",
};

/*
 * For easier debugging in development mode, you can import the following file
 * to ignore zone related error stack frames such as `zone.run`, `zoneDelegate.invokeTask`.
 *
 * This import should be commented out in production mode because it will have a negative impact
 * on performance if an error is thrown.
 */
// import 'zone.js/dist/zone-error';  // Included with Angular CLI.
