import { Component, OnInit } from "@angular/core";

@Component({
	selector: "app-hymnal",
	templateUrl: "./hymnal.component.html",
	styleUrls: ["./hymnal.component.css"],
})
export class HymnalComponent implements OnInit {
	constructor() {}

	ngOnInit(): void {
		window.location.href = "https://fothuzone-rosary.s3.amazonaws.com/cp3u.pdf";
	}
}
