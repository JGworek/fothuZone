import { Component, ElementRef, HostListener, OnInit, ViewChild } from "@angular/core";
import { Square } from "../models/Square";

function drawDonutChart(canvas) {
	this.x, this.y, this.radius, this.lineWidth, this.strockStyle, this.from, (this.to = null);
	this.set = function (x, y, radius, from, to, lineWidth, strockStyle) {
		this.x = x;
		this.y = y;
		this.radius = radius;
		this.from = from;
		this.to = to;
		this.lineWidth = lineWidth;
		this.strockStyle = strockStyle;
	};

	this.draw = function (data) {
		canvas.beginPath();
		canvas.lineWidth = this.lineWidth;
		canvas.strokeStyle = this.strockStyle;
		canvas.arc(this.x, this.y, this.radius, this.from, this.to);
		canvas.stroke();
		var numberOfParts = data.numberOfParts;
		var parts = data.parts.pt;
		var colors = data.colors.cs;
		var df = 0;
		for (var i = 0; i < numberOfParts; i++) {
			canvas.beginPath();
			canvas.strokeStyle = colors[i];
			canvas.arc(this.x, this.y, this.radius, df, df + Math.PI * 2 * (parts[i] / 100));
			canvas.stroke();
			df += Math.PI * 2 * (parts[i] / 100);
		}
	};
}

function drawLine(ctx, startX, startY, endX, endY, color) {
	ctx.save();
	ctx.strokeStyle = color;
	ctx.beginPath();
	ctx.moveTo(startX, startY);
	ctx.lineTo(endX, endY);
	ctx.stroke();
	ctx.restore();
}



@Component({
	selector: "app-salfagge-visualizer",
	templateUrl: "./salfagge-visualizer.component.html",
	styleUrls: ["./salfagge-visualizer.component.css"],
	styles: ["canvas { border-style: solid }"],
})
export class SalfaggeVisualizerComponent implements OnInit {
	constructor() {}

	tone = {
		C: [16.35, 32.7, 65.41, 130.81, 261.63, 523.25, 1046.5, 2093.0, 4186.01],
		Db: [17.32, 34.65, 69.3, 138.59, 277.18, 554.37, 1108.73, 2217.46, 4434.92],
		D: [18.35, 36.71, 73.42, 146.83, 293.66, 587.33, 1174.66, 2349.32, 4698.64],
		Eb: [19.45, 38.89, 77.78, 155.56, 311.13, 622.25, 1244.51, 2489.02, 4978.03],
		E: [20.6, 41.2, 82.41, 164.81, 329.63, 659.26, 1318.51, 2637.02],
		F: [21.83, 43.65, 87.31, 174.61, 349.23, 698.46, 1396.91, 2793.83],
		Gb: [23.12, 46.25, 92.5, 185.0, 369.99, 739.99, 1479.98, 2959.96],
		G: [24.5, 49.0, 98.0, 196.0, 392.0, 783.99, 1567.98, 3135.96],
		Ab: [25.96, 51.91, 103.83, 207.65, 415.3, 830.61, 1661.22, 3322.44],
		A: [27.5, 55.0, 110.0, 220.0, 440.0, 880.0, 1760.0, 3520.0],
		Bb: [29.14, 58.27, 116.54, 233.08, 466.16, 932.33, 1864.66, 3729.31],
		B: [30.87, 61.74, 123.47, 246.94, 493.88, 987.77, 1975.53, 3951.07],
	};

	defaultColors = ["#047DFF", "#0331F6", "#7E3AFA", "#FA40FD", "#FF2C7D", "#FF2400", "#FE7E05", "#FFF104", "#81E306", "#08DC01", "#04E07B", "#03E9FC"];
	lowercaseAlphabet = ["a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z"];
	directionKeys = ["ArrowUp", "ArrowDown", "ArrowLeft", "ArrowRight"];
	pitchKey: string = "";
	octaveKey: string = "";
	AudioContext = window.AudioContext;
	audioContext;
	oscillator;
	currentNote: string = "";
	ringData = {
		numberOfParts: 12,
		parts: { pt: [8.333, 8.333, 8.333, 8.333, 8.333, 8.333, 8.333, 8.333, 8.333, 8.333, 8.333, 8.333] }, //percentage of each parts
		colors: { cs: [this.defaultColors[0],this.defaultColors[1],this.defaultColors[2],this.defaultColors[3],this.defaultColors[4],this.defaultColors[5],this.defaultColors[6],this.defaultColors[7],this.defaultColors[8],this.defaultColors[9],this.defaultColors[10],this.defaultColors[11]] }, //color of each part
	};
	xCoordinate = 587;
	yCoordinate = 590;
	ringWidth = 40;
	outlineColor = "#000000";



	@ViewChild("canvas", { static: true })
	canvas: ElementRef<HTMLCanvasElement>;
	ctx: CanvasRenderingContext2D;
	square;

	@HostListener("window:keydown", ["$event"])
	handleKeyDownEvent(event: KeyboardEvent) {
		if (this.checkLowerCaseAlphabet(event.key)) {
			this.pitchKey = event.key;
		}
		if (this.checkDirectionKeys(event.key)) {
			this.octaveKey = event.key;
		}
		if (this.pitchKey === "a" && this.octaveKey === "ArrowDown") {
			this.oscillator.frequency.value = 440.0;
			this.ringData.colors.cs[0] = "#000000";
			let drawDonutFive = new drawDonutChart(this.ctx);
		drawDonutFive.set(this.xCoordinate, this.yCoordinate, 360, 0, Math.PI * 2, this.ringWidth, "#fff");
		drawDonutFive.draw(this.ringData);

		this.ctx.beginPath();
		this.ctx.arc(this.xCoordinate, this.yCoordinate, 540, 0, 50 * Math.PI);
		this.ctx.lineWidth = 3;
		this.ctx.strokeStyle = this.outlineColor;
		this.ctx.stroke();
		this.ctx.beginPath();
		this.ctx.arc(this.xCoordinate, this.yCoordinate, 500, 0, 50 * Math.PI);
		this.ctx.lineWidth = 3;
		this.ctx.strokeStyle = this.outlineColor;
		this.ctx.stroke();
		this.ctx.beginPath();
		this.ctx.arc(this.xCoordinate, this.yCoordinate, 460, 0, 50 * Math.PI);
		this.ctx.lineWidth = 3;
		this.ctx.strokeStyle = this.outlineColor;
		this.ctx.stroke();
		this.ctx.beginPath();
		this.ctx.arc(this.xCoordinate, this.yCoordinate, 420, 0, 50 * Math.PI);
		this.ctx.lineWidth = 3;
		this.ctx.strokeStyle =this.outlineColor;
		this.ctx.stroke();
		this.ctx.beginPath();
		this.ctx.arc(this.xCoordinate, this.yCoordinate, 380, 0, 50 * Math.PI);
		this.ctx.lineWidth = 3;
		this.ctx.strokeStyle = this.outlineColor;
		this.ctx.stroke();
		this.ctx.beginPath();
		this.ctx.arc(this.xCoordinate, this.yCoordinate, 340, 0, 50 * Math.PI);
		this.ctx.lineWidth = 3;
		this.ctx.strokeStyle = this.outlineColor;
		this.ctx.stroke();
		this.ctx.beginPath();
		this.ctx.arc(this.xCoordinate, this.yCoordinate, 300, 0, 50 * Math.PI);
		this.ctx.lineWidth = 3;
		this.ctx.strokeStyle = this.outlineColor;
		this.ctx.stroke();
		this.ctx.beginPath();
		this.ctx.arc(this.xCoordinate, this.yCoordinate, 260, 0, 50 * Math.PI);
		this.ctx.lineWidth = 3;
		this.ctx.strokeStyle = this.outlineColor;
		this.ctx.stroke();
		this.ctx.beginPath();
		this.ctx.arc(this.xCoordinate, this.yCoordinate, 220, 0, 50 * Math.PI);
		this.ctx.lineWidth = 3;
		this.ctx.strokeStyle = this.outlineColor;
		this.ctx.stroke();
		this.ctx.beginPath();
		this.ctx.arc(this.xCoordinate, this.yCoordinate, 180, 0, 50 * Math.PI);
		this.ctx.lineWidth = 3;
		this.ctx.strokeStyle = this.outlineColor;
		this.ctx.stroke();

		drawLine(this.ctx, 590, 50, 588, 410, this.outlineColor);
		drawLine(this.ctx, 317, 122, 498, 432, this.outlineColor);
		drawLine(this.ctx, 120, 320, 430, 500, this.outlineColor);
		drawLine(this.ctx, 48, 590, 408, 590, this.outlineColor);
		drawLine(this.ctx, 120, 860, 430, 680, this.outlineColor);
		drawLine(this.ctx, 317, 1056, 498, 745, this.outlineColor);
		drawLine(this.ctx, 588, 1128, 586, 768, this.outlineColor);
		drawLine(this.ctx, 676, 746, 857, 1058,this.outlineColor);
		drawLine(this.ctx, 743, 680, 1056, 860,this.outlineColor);
		drawLine(this.ctx, 676, 746, 857, 1058, this.outlineColor);
		drawLine(this.ctx, 768, 590, 1126, 591, this.outlineColor);
		drawLine(this.ctx, 744, 500, 1055, 320, this.outlineColor);
		drawLine(this.ctx, 676, 434, 857, 122, this.outlineColor);


			this.currentNote = "A4";
			this.audioContext.resume();
		}
		if (this.pitchKey === "s" && this.octaveKey === "ArrowDown") {
			this.oscillator.frequency.value = 493.88;

			this.ringData.colors.cs[2] = "#000000";
			let drawDonutFive = new drawDonutChart(this.ctx);
		drawDonutFive.set(this.xCoordinate, this.yCoordinate, 360, 0, Math.PI * 2, this.ringWidth, "#fff");
		drawDonutFive.draw(this.ringData);

		this.ctx.beginPath();
		this.ctx.arc(this.xCoordinate, this.yCoordinate, 540, 0, 50 * Math.PI);
		this.ctx.lineWidth = 3;
		this.ctx.strokeStyle = this.outlineColor;
		this.ctx.stroke();
		this.ctx.beginPath();
		this.ctx.arc(this.xCoordinate, this.yCoordinate, 500, 0, 50 * Math.PI);
		this.ctx.lineWidth = 3;
		this.ctx.strokeStyle = this.outlineColor;
		this.ctx.stroke();
		this.ctx.beginPath();
		this.ctx.arc(this.xCoordinate, this.yCoordinate, 460, 0, 50 * Math.PI);
		this.ctx.lineWidth = 3;
		this.ctx.strokeStyle = this.outlineColor;
		this.ctx.stroke();
		this.ctx.beginPath();
		this.ctx.arc(this.xCoordinate, this.yCoordinate, 420, 0, 50 * Math.PI);
		this.ctx.lineWidth = 3;
		this.ctx.strokeStyle =this.outlineColor;
		this.ctx.stroke();
		this.ctx.beginPath();
		this.ctx.arc(this.xCoordinate, this.yCoordinate, 380, 0, 50 * Math.PI);
		this.ctx.lineWidth = 3;
		this.ctx.strokeStyle = this.outlineColor;
		this.ctx.stroke();
		this.ctx.beginPath();
		this.ctx.arc(this.xCoordinate, this.yCoordinate, 340, 0, 50 * Math.PI);
		this.ctx.lineWidth = 3;
		this.ctx.strokeStyle = this.outlineColor;
		this.ctx.stroke();
		this.ctx.beginPath();
		this.ctx.arc(this.xCoordinate, this.yCoordinate, 300, 0, 50 * Math.PI);
		this.ctx.lineWidth = 3;
		this.ctx.strokeStyle = this.outlineColor;
		this.ctx.stroke();
		this.ctx.beginPath();
		this.ctx.arc(this.xCoordinate, this.yCoordinate, 260, 0, 50 * Math.PI);
		this.ctx.lineWidth = 3;
		this.ctx.strokeStyle = this.outlineColor;
		this.ctx.stroke();
		this.ctx.beginPath();
		this.ctx.arc(this.xCoordinate, this.yCoordinate, 220, 0, 50 * Math.PI);
		this.ctx.lineWidth = 3;
		this.ctx.strokeStyle = this.outlineColor;
		this.ctx.stroke();
		this.ctx.beginPath();
		this.ctx.arc(this.xCoordinate, this.yCoordinate, 180, 0, 50 * Math.PI);
		this.ctx.lineWidth = 3;
		this.ctx.strokeStyle = this.outlineColor;
		this.ctx.stroke();

		drawLine(this.ctx, 590, 50, 588, 410, this.outlineColor);
		drawLine(this.ctx, 317, 122, 498, 432, this.outlineColor);
		drawLine(this.ctx, 120, 320, 430, 500, this.outlineColor);
		drawLine(this.ctx, 48, 590, 408, 590, this.outlineColor);
		drawLine(this.ctx, 120, 860, 430, 680, this.outlineColor);
		drawLine(this.ctx, 317, 1056, 498, 745, this.outlineColor);
		drawLine(this.ctx, 588, 1128, 586, 768, this.outlineColor);
		drawLine(this.ctx, 676, 746, 857, 1058,this.outlineColor);
		drawLine(this.ctx, 743, 680, 1056, 860,this.outlineColor);
		drawLine(this.ctx, 676, 746, 857, 1058, this.outlineColor);
		drawLine(this.ctx, 768, 590, 1126, 591, this.outlineColor);
		drawLine(this.ctx, 744, 500, 1055, 320, this.outlineColor);
		drawLine(this.ctx, 676, 434, 857, 122, this.outlineColor);

			this.currentNote = "B4";
			this.audioContext.resume();
		}
		if (this.pitchKey === "d" && this.octaveKey === "ArrowDown") {
			this.oscillator.frequency.value = 554.37;

			this.ringData.colors.cs[4] = "#000000";
			let drawDonutFive = new drawDonutChart(this.ctx);
		drawDonutFive.set(this.xCoordinate, this.yCoordinate, 360, 0, Math.PI * 2, this.ringWidth, "#fff");
		drawDonutFive.draw(this.ringData);

		this.ctx.beginPath();
		this.ctx.arc(this.xCoordinate, this.yCoordinate, 540, 0, 50 * Math.PI);
		this.ctx.lineWidth = 3;
		this.ctx.strokeStyle = this.outlineColor;
		this.ctx.stroke();
		this.ctx.beginPath();
		this.ctx.arc(this.xCoordinate, this.yCoordinate, 500, 0, 50 * Math.PI);
		this.ctx.lineWidth = 3;
		this.ctx.strokeStyle = this.outlineColor;
		this.ctx.stroke();
		this.ctx.beginPath();
		this.ctx.arc(this.xCoordinate, this.yCoordinate, 460, 0, 50 * Math.PI);
		this.ctx.lineWidth = 3;
		this.ctx.strokeStyle = this.outlineColor;
		this.ctx.stroke();
		this.ctx.beginPath();
		this.ctx.arc(this.xCoordinate, this.yCoordinate, 420, 0, 50 * Math.PI);
		this.ctx.lineWidth = 3;
		this.ctx.strokeStyle =this.outlineColor;
		this.ctx.stroke();
		this.ctx.beginPath();
		this.ctx.arc(this.xCoordinate, this.yCoordinate, 380, 0, 50 * Math.PI);
		this.ctx.lineWidth = 3;
		this.ctx.strokeStyle = this.outlineColor;
		this.ctx.stroke();
		this.ctx.beginPath();
		this.ctx.arc(this.xCoordinate, this.yCoordinate, 340, 0, 50 * Math.PI);
		this.ctx.lineWidth = 3;
		this.ctx.strokeStyle = this.outlineColor;
		this.ctx.stroke();
		this.ctx.beginPath();
		this.ctx.arc(this.xCoordinate, this.yCoordinate, 300, 0, 50 * Math.PI);
		this.ctx.lineWidth = 3;
		this.ctx.strokeStyle = this.outlineColor;
		this.ctx.stroke();
		this.ctx.beginPath();
		this.ctx.arc(this.xCoordinate, this.yCoordinate, 260, 0, 50 * Math.PI);
		this.ctx.lineWidth = 3;
		this.ctx.strokeStyle = this.outlineColor;
		this.ctx.stroke();
		this.ctx.beginPath();
		this.ctx.arc(this.xCoordinate, this.yCoordinate, 220, 0, 50 * Math.PI);
		this.ctx.lineWidth = 3;
		this.ctx.strokeStyle = this.outlineColor;
		this.ctx.stroke();
		this.ctx.beginPath();
		this.ctx.arc(this.xCoordinate, this.yCoordinate, 180, 0, 50 * Math.PI);
		this.ctx.lineWidth = 3;
		this.ctx.strokeStyle = this.outlineColor;
		this.ctx.stroke();

		drawLine(this.ctx, 590, 50, 588, 410, this.outlineColor);
		drawLine(this.ctx, 317, 122, 498, 432, this.outlineColor);
		drawLine(this.ctx, 120, 320, 430, 500, this.outlineColor);
		drawLine(this.ctx, 48, 590, 408, 590, this.outlineColor);
		drawLine(this.ctx, 120, 860, 430, 680, this.outlineColor);
		drawLine(this.ctx, 317, 1056, 498, 745, this.outlineColor);
		drawLine(this.ctx, 588, 1128, 586, 768, this.outlineColor);
		drawLine(this.ctx, 676, 746, 857, 1058,this.outlineColor);
		drawLine(this.ctx, 743, 680, 1056, 860,this.outlineColor);
		drawLine(this.ctx, 676, 746, 857, 1058, this.outlineColor);
		drawLine(this.ctx, 768, 590, 1126, 591, this.outlineColor);
		drawLine(this.ctx, 744, 500, 1055, 320, this.outlineColor);
		drawLine(this.ctx, 676, 434, 857, 122, this.outlineColor);

			this.currentNote = "C#5";
			this.audioContext.resume();
		}
		if (this.pitchKey === "f" && this.octaveKey === "ArrowDown") {
			this.oscillator.frequency.value = 659.25;

			this.ringData.colors.cs[7] = "#000000";
			let drawDonutFive = new drawDonutChart(this.ctx);
		drawDonutFive.set(this.xCoordinate, this.yCoordinate, 360, 0, Math.PI * 2, this.ringWidth, "#fff");
		drawDonutFive.draw(this.ringData);

		this.ctx.beginPath();
		this.ctx.arc(this.xCoordinate, this.yCoordinate, 540, 0, 50 * Math.PI);
		this.ctx.lineWidth = 3;
		this.ctx.strokeStyle = this.outlineColor;
		this.ctx.stroke();
		this.ctx.beginPath();
		this.ctx.arc(this.xCoordinate, this.yCoordinate, 500, 0, 50 * Math.PI);
		this.ctx.lineWidth = 3;
		this.ctx.strokeStyle = this.outlineColor;
		this.ctx.stroke();
		this.ctx.beginPath();
		this.ctx.arc(this.xCoordinate, this.yCoordinate, 460, 0, 50 * Math.PI);
		this.ctx.lineWidth = 3;
		this.ctx.strokeStyle = this.outlineColor;
		this.ctx.stroke();
		this.ctx.beginPath();
		this.ctx.arc(this.xCoordinate, this.yCoordinate, 420, 0, 50 * Math.PI);
		this.ctx.lineWidth = 3;
		this.ctx.strokeStyle =this.outlineColor;
		this.ctx.stroke();
		this.ctx.beginPath();
		this.ctx.arc(this.xCoordinate, this.yCoordinate, 380, 0, 50 * Math.PI);
		this.ctx.lineWidth = 3;
		this.ctx.strokeStyle = this.outlineColor;
		this.ctx.stroke();
		this.ctx.beginPath();
		this.ctx.arc(this.xCoordinate, this.yCoordinate, 340, 0, 50 * Math.PI);
		this.ctx.lineWidth = 3;
		this.ctx.strokeStyle = this.outlineColor;
		this.ctx.stroke();
		this.ctx.beginPath();
		this.ctx.arc(this.xCoordinate, this.yCoordinate, 300, 0, 50 * Math.PI);
		this.ctx.lineWidth = 3;
		this.ctx.strokeStyle = this.outlineColor;
		this.ctx.stroke();
		this.ctx.beginPath();
		this.ctx.arc(this.xCoordinate, this.yCoordinate, 260, 0, 50 * Math.PI);
		this.ctx.lineWidth = 3;
		this.ctx.strokeStyle = this.outlineColor;
		this.ctx.stroke();
		this.ctx.beginPath();
		this.ctx.arc(this.xCoordinate, this.yCoordinate, 220, 0, 50 * Math.PI);
		this.ctx.lineWidth = 3;
		this.ctx.strokeStyle = this.outlineColor;
		this.ctx.stroke();
		this.ctx.beginPath();
		this.ctx.arc(this.xCoordinate, this.yCoordinate, 180, 0, 50 * Math.PI);
		this.ctx.lineWidth = 3;
		this.ctx.strokeStyle = this.outlineColor;
		this.ctx.stroke();

		drawLine(this.ctx, 590, 50, 588, 410, this.outlineColor);
		drawLine(this.ctx, 317, 122, 498, 432, this.outlineColor);
		drawLine(this.ctx, 120, 320, 430, 500, this.outlineColor);
		drawLine(this.ctx, 48, 590, 408, 590, this.outlineColor);
		drawLine(this.ctx, 120, 860, 430, 680, this.outlineColor);
		drawLine(this.ctx, 317, 1056, 498, 745, this.outlineColor);
		drawLine(this.ctx, 588, 1128, 586, 768, this.outlineColor);
		drawLine(this.ctx, 676, 746, 857, 1058,this.outlineColor);
		drawLine(this.ctx, 743, 680, 1056, 860,this.outlineColor);
		drawLine(this.ctx, 676, 746, 857, 1058, this.outlineColor);
		drawLine(this.ctx, 768, 590, 1126, 591, this.outlineColor);
		drawLine(this.ctx, 744, 500, 1055, 320, this.outlineColor);
		drawLine(this.ctx, 676, 434, 857, 122, this.outlineColor);

			this.currentNote = "E5";
			this.audioContext.resume();
		}
		if (this.pitchKey === "g" && this.octaveKey === "ArrowDown") {
			this.oscillator.frequency.value = 739.99;

			this.ringData.colors.cs[9] = "#000000";
			let drawDonutFive = new drawDonutChart(this.ctx);
		drawDonutFive.set(this.xCoordinate, this.yCoordinate, 360, 0, Math.PI * 2, this.ringWidth, "#fff");
		drawDonutFive.draw(this.ringData);

		this.ctx.beginPath();
		this.ctx.arc(this.xCoordinate, this.yCoordinate, 540, 0, 50 * Math.PI);
		this.ctx.lineWidth = 3;
		this.ctx.strokeStyle = this.outlineColor;
		this.ctx.stroke();
		this.ctx.beginPath();
		this.ctx.arc(this.xCoordinate, this.yCoordinate, 500, 0, 50 * Math.PI);
		this.ctx.lineWidth = 3;
		this.ctx.strokeStyle = this.outlineColor;
		this.ctx.stroke();
		this.ctx.beginPath();
		this.ctx.arc(this.xCoordinate, this.yCoordinate, 460, 0, 50 * Math.PI);
		this.ctx.lineWidth = 3;
		this.ctx.strokeStyle = this.outlineColor;
		this.ctx.stroke();
		this.ctx.beginPath();
		this.ctx.arc(this.xCoordinate, this.yCoordinate, 420, 0, 50 * Math.PI);
		this.ctx.lineWidth = 3;
		this.ctx.strokeStyle =this.outlineColor;
		this.ctx.stroke();
		this.ctx.beginPath();
		this.ctx.arc(this.xCoordinate, this.yCoordinate, 380, 0, 50 * Math.PI);
		this.ctx.lineWidth = 3;
		this.ctx.strokeStyle = this.outlineColor;
		this.ctx.stroke();
		this.ctx.beginPath();
		this.ctx.arc(this.xCoordinate, this.yCoordinate, 340, 0, 50 * Math.PI);
		this.ctx.lineWidth = 3;
		this.ctx.strokeStyle = this.outlineColor;
		this.ctx.stroke();
		this.ctx.beginPath();
		this.ctx.arc(this.xCoordinate, this.yCoordinate, 300, 0, 50 * Math.PI);
		this.ctx.lineWidth = 3;
		this.ctx.strokeStyle = this.outlineColor;
		this.ctx.stroke();
		this.ctx.beginPath();
		this.ctx.arc(this.xCoordinate, this.yCoordinate, 260, 0, 50 * Math.PI);
		this.ctx.lineWidth = 3;
		this.ctx.strokeStyle = this.outlineColor;
		this.ctx.stroke();
		this.ctx.beginPath();
		this.ctx.arc(this.xCoordinate, this.yCoordinate, 220, 0, 50 * Math.PI);
		this.ctx.lineWidth = 3;
		this.ctx.strokeStyle = this.outlineColor;
		this.ctx.stroke();
		this.ctx.beginPath();
		this.ctx.arc(this.xCoordinate, this.yCoordinate, 180, 0, 50 * Math.PI);
		this.ctx.lineWidth = 3;
		this.ctx.strokeStyle = this.outlineColor;
		this.ctx.stroke();

		drawLine(this.ctx, 590, 50, 588, 410, this.outlineColor);
		drawLine(this.ctx, 317, 122, 498, 432, this.outlineColor);
		drawLine(this.ctx, 120, 320, 430, 500, this.outlineColor);
		drawLine(this.ctx, 48, 590, 408, 590, this.outlineColor);
		drawLine(this.ctx, 120, 860, 430, 680, this.outlineColor);
		drawLine(this.ctx, 317, 1056, 498, 745, this.outlineColor);
		drawLine(this.ctx, 588, 1128, 586, 768, this.outlineColor);
		drawLine(this.ctx, 676, 746, 857, 1058,this.outlineColor);
		drawLine(this.ctx, 743, 680, 1056, 860,this.outlineColor);
		drawLine(this.ctx, 676, 746, 857, 1058, this.outlineColor);
		drawLine(this.ctx, 768, 590, 1126, 591, this.outlineColor);
		drawLine(this.ctx, 744, 500, 1055, 320, this.outlineColor);
		drawLine(this.ctx, 676, 434, 857, 122, this.outlineColor);

			this.currentNote = "F#5";
			this.audioContext.resume();
		}

		if (this.pitchKey === "a" && this.octaveKey === "ArrowLeft") {
			this.oscillator.frequency.value = 220.0;

			this.ringData.colors.cs[0] = "#000000";
			let drawDonutFour = new drawDonutChart(this.ctx);
		drawDonutFour.set(this.xCoordinate, this.yCoordinate, 400, 0, Math.PI * 2, this.ringWidth, "#fff");
		drawDonutFour.draw(this.ringData);

		this.ctx.beginPath();
		this.ctx.arc(this.xCoordinate, this.yCoordinate, 540, 0, 50 * Math.PI);
		this.ctx.lineWidth = 3;
		this.ctx.strokeStyle = this.outlineColor;
		this.ctx.stroke();
		this.ctx.beginPath();
		this.ctx.arc(this.xCoordinate, this.yCoordinate, 500, 0, 50 * Math.PI);
		this.ctx.lineWidth = 3;
		this.ctx.strokeStyle = this.outlineColor;
		this.ctx.stroke();
		this.ctx.beginPath();
		this.ctx.arc(this.xCoordinate, this.yCoordinate, 460, 0, 50 * Math.PI);
		this.ctx.lineWidth = 3;
		this.ctx.strokeStyle = this.outlineColor;
		this.ctx.stroke();
		this.ctx.beginPath();
		this.ctx.arc(this.xCoordinate, this.yCoordinate, 420, 0, 50 * Math.PI);
		this.ctx.lineWidth = 3;
		this.ctx.strokeStyle =this.outlineColor;
		this.ctx.stroke();
		this.ctx.beginPath();
		this.ctx.arc(this.xCoordinate, this.yCoordinate, 380, 0, 50 * Math.PI);
		this.ctx.lineWidth = 3;
		this.ctx.strokeStyle = this.outlineColor;
		this.ctx.stroke();
		this.ctx.beginPath();
		this.ctx.arc(this.xCoordinate, this.yCoordinate, 340, 0, 50 * Math.PI);
		this.ctx.lineWidth = 3;
		this.ctx.strokeStyle = this.outlineColor;
		this.ctx.stroke();
		this.ctx.beginPath();
		this.ctx.arc(this.xCoordinate, this.yCoordinate, 300, 0, 50 * Math.PI);
		this.ctx.lineWidth = 3;
		this.ctx.strokeStyle = this.outlineColor;
		this.ctx.stroke();
		this.ctx.beginPath();
		this.ctx.arc(this.xCoordinate, this.yCoordinate, 260, 0, 50 * Math.PI);
		this.ctx.lineWidth = 3;
		this.ctx.strokeStyle = this.outlineColor;
		this.ctx.stroke();
		this.ctx.beginPath();
		this.ctx.arc(this.xCoordinate, this.yCoordinate, 220, 0, 50 * Math.PI);
		this.ctx.lineWidth = 3;
		this.ctx.strokeStyle = this.outlineColor;
		this.ctx.stroke();
		this.ctx.beginPath();
		this.ctx.arc(this.xCoordinate, this.yCoordinate, 180, 0, 50 * Math.PI);
		this.ctx.lineWidth = 3;
		this.ctx.strokeStyle = this.outlineColor;
		this.ctx.stroke();

		drawLine(this.ctx, 590, 50, 588, 410, this.outlineColor);
		drawLine(this.ctx, 317, 122, 498, 432, this.outlineColor);
		drawLine(this.ctx, 120, 320, 430, 500, this.outlineColor);
		drawLine(this.ctx, 48, 590, 408, 590, this.outlineColor);
		drawLine(this.ctx, 120, 860, 430, 680, this.outlineColor);
		drawLine(this.ctx, 317, 1056, 498, 745, this.outlineColor);
		drawLine(this.ctx, 588, 1128, 586, 768, this.outlineColor);
		drawLine(this.ctx, 676, 746, 857, 1058,this.outlineColor);
		drawLine(this.ctx, 743, 680, 1056, 860,this.outlineColor);
		drawLine(this.ctx, 676, 746, 857, 1058, this.outlineColor);
		drawLine(this.ctx, 768, 590, 1126, 591, this.outlineColor);
		drawLine(this.ctx, 744, 500, 1055, 320, this.outlineColor);
		drawLine(this.ctx, 676, 434, 857, 122, this.outlineColor);

			this.currentNote = "A3";
			this.audioContext.resume();
		}
		if (this.pitchKey === "s" && this.octaveKey === "ArrowLeft") {
			this.oscillator.frequency.value = 246.94;

			this.ringData.colors.cs[2] = "#000000";
			let drawDonutFour = new drawDonutChart(this.ctx);
		drawDonutFour.set(this.xCoordinate, this.yCoordinate, 400, 0, Math.PI * 2, this.ringWidth, "#fff");
		drawDonutFour.draw(this.ringData);

		this.ctx.beginPath();
		this.ctx.arc(this.xCoordinate, this.yCoordinate, 540, 0, 50 * Math.PI);
		this.ctx.lineWidth = 3;
		this.ctx.strokeStyle = this.outlineColor;
		this.ctx.stroke();
		this.ctx.beginPath();
		this.ctx.arc(this.xCoordinate, this.yCoordinate, 500, 0, 50 * Math.PI);
		this.ctx.lineWidth = 3;
		this.ctx.strokeStyle = this.outlineColor;
		this.ctx.stroke();
		this.ctx.beginPath();
		this.ctx.arc(this.xCoordinate, this.yCoordinate, 460, 0, 50 * Math.PI);
		this.ctx.lineWidth = 3;
		this.ctx.strokeStyle = this.outlineColor;
		this.ctx.stroke();
		this.ctx.beginPath();
		this.ctx.arc(this.xCoordinate, this.yCoordinate, 420, 0, 50 * Math.PI);
		this.ctx.lineWidth = 3;
		this.ctx.strokeStyle =this.outlineColor;
		this.ctx.stroke();
		this.ctx.beginPath();
		this.ctx.arc(this.xCoordinate, this.yCoordinate, 380, 0, 50 * Math.PI);
		this.ctx.lineWidth = 3;
		this.ctx.strokeStyle = this.outlineColor;
		this.ctx.stroke();
		this.ctx.beginPath();
		this.ctx.arc(this.xCoordinate, this.yCoordinate, 340, 0, 50 * Math.PI);
		this.ctx.lineWidth = 3;
		this.ctx.strokeStyle = this.outlineColor;
		this.ctx.stroke();
		this.ctx.beginPath();
		this.ctx.arc(this.xCoordinate, this.yCoordinate, 300, 0, 50 * Math.PI);
		this.ctx.lineWidth = 3;
		this.ctx.strokeStyle = this.outlineColor;
		this.ctx.stroke();
		this.ctx.beginPath();
		this.ctx.arc(this.xCoordinate, this.yCoordinate, 260, 0, 50 * Math.PI);
		this.ctx.lineWidth = 3;
		this.ctx.strokeStyle = this.outlineColor;
		this.ctx.stroke();
		this.ctx.beginPath();
		this.ctx.arc(this.xCoordinate, this.yCoordinate, 220, 0, 50 * Math.PI);
		this.ctx.lineWidth = 3;
		this.ctx.strokeStyle = this.outlineColor;
		this.ctx.stroke();
		this.ctx.beginPath();
		this.ctx.arc(this.xCoordinate, this.yCoordinate, 180, 0, 50 * Math.PI);
		this.ctx.lineWidth = 3;
		this.ctx.strokeStyle = this.outlineColor;
		this.ctx.stroke();

		drawLine(this.ctx, 590, 50, 588, 410, this.outlineColor);
		drawLine(this.ctx, 317, 122, 498, 432, this.outlineColor);
		drawLine(this.ctx, 120, 320, 430, 500, this.outlineColor);
		drawLine(this.ctx, 48, 590, 408, 590, this.outlineColor);
		drawLine(this.ctx, 120, 860, 430, 680, this.outlineColor);
		drawLine(this.ctx, 317, 1056, 498, 745, this.outlineColor);
		drawLine(this.ctx, 588, 1128, 586, 768, this.outlineColor);
		drawLine(this.ctx, 676, 746, 857, 1058,this.outlineColor);
		drawLine(this.ctx, 743, 680, 1056, 860,this.outlineColor);
		drawLine(this.ctx, 676, 746, 857, 1058, this.outlineColor);
		drawLine(this.ctx, 768, 590, 1126, 591, this.outlineColor);
		drawLine(this.ctx, 744, 500, 1055, 320, this.outlineColor);
		drawLine(this.ctx, 676, 434, 857, 122, this.outlineColor);

			this.currentNote = "B3";
			this.audioContext.resume();
		}
		if (this.pitchKey === "d" && this.octaveKey === "ArrowLeft") {
			this.oscillator.frequency.value = 277.18;

			this.ringData.colors.cs[4] = "#000000";
			let drawDonutFour = new drawDonutChart(this.ctx);
		drawDonutFour.set(this.xCoordinate, this.yCoordinate, 400, 0, Math.PI * 2, this.ringWidth, "#fff");
		drawDonutFour.draw(this.ringData);

		this.ctx.beginPath();
		this.ctx.arc(this.xCoordinate, this.yCoordinate, 540, 0, 50 * Math.PI);
		this.ctx.lineWidth = 3;
		this.ctx.strokeStyle = this.outlineColor;
		this.ctx.stroke();
		this.ctx.beginPath();
		this.ctx.arc(this.xCoordinate, this.yCoordinate, 500, 0, 50 * Math.PI);
		this.ctx.lineWidth = 3;
		this.ctx.strokeStyle = this.outlineColor;
		this.ctx.stroke();
		this.ctx.beginPath();
		this.ctx.arc(this.xCoordinate, this.yCoordinate, 460, 0, 50 * Math.PI);
		this.ctx.lineWidth = 3;
		this.ctx.strokeStyle = this.outlineColor;
		this.ctx.stroke();
		this.ctx.beginPath();
		this.ctx.arc(this.xCoordinate, this.yCoordinate, 420, 0, 50 * Math.PI);
		this.ctx.lineWidth = 3;
		this.ctx.strokeStyle =this.outlineColor;
		this.ctx.stroke();
		this.ctx.beginPath();
		this.ctx.arc(this.xCoordinate, this.yCoordinate, 380, 0, 50 * Math.PI);
		this.ctx.lineWidth = 3;
		this.ctx.strokeStyle = this.outlineColor;
		this.ctx.stroke();
		this.ctx.beginPath();
		this.ctx.arc(this.xCoordinate, this.yCoordinate, 340, 0, 50 * Math.PI);
		this.ctx.lineWidth = 3;
		this.ctx.strokeStyle = this.outlineColor;
		this.ctx.stroke();
		this.ctx.beginPath();
		this.ctx.arc(this.xCoordinate, this.yCoordinate, 300, 0, 50 * Math.PI);
		this.ctx.lineWidth = 3;
		this.ctx.strokeStyle = this.outlineColor;
		this.ctx.stroke();
		this.ctx.beginPath();
		this.ctx.arc(this.xCoordinate, this.yCoordinate, 260, 0, 50 * Math.PI);
		this.ctx.lineWidth = 3;
		this.ctx.strokeStyle = this.outlineColor;
		this.ctx.stroke();
		this.ctx.beginPath();
		this.ctx.arc(this.xCoordinate, this.yCoordinate, 220, 0, 50 * Math.PI);
		this.ctx.lineWidth = 3;
		this.ctx.strokeStyle = this.outlineColor;
		this.ctx.stroke();
		this.ctx.beginPath();
		this.ctx.arc(this.xCoordinate, this.yCoordinate, 180, 0, 50 * Math.PI);
		this.ctx.lineWidth = 3;
		this.ctx.strokeStyle = this.outlineColor;
		this.ctx.stroke();

		drawLine(this.ctx, 590, 50, 588, 410, this.outlineColor);
		drawLine(this.ctx, 317, 122, 498, 432, this.outlineColor);
		drawLine(this.ctx, 120, 320, 430, 500, this.outlineColor);
		drawLine(this.ctx, 48, 590, 408, 590, this.outlineColor);
		drawLine(this.ctx, 120, 860, 430, 680, this.outlineColor);
		drawLine(this.ctx, 317, 1056, 498, 745, this.outlineColor);
		drawLine(this.ctx, 588, 1128, 586, 768, this.outlineColor);
		drawLine(this.ctx, 676, 746, 857, 1058,this.outlineColor);
		drawLine(this.ctx, 743, 680, 1056, 860,this.outlineColor);
		drawLine(this.ctx, 676, 746, 857, 1058, this.outlineColor);
		drawLine(this.ctx, 768, 590, 1126, 591, this.outlineColor);
		drawLine(this.ctx, 744, 500, 1055, 320, this.outlineColor);
		drawLine(this.ctx, 676, 434, 857, 122, this.outlineColor);

			this.currentNote = "C#4";
			this.audioContext.resume();
		}
		if (this.pitchKey === "f" && this.octaveKey === "ArrowLeft") {
			this.oscillator.frequency.value = 329.63;

			this.ringData.colors.cs[7] = "#000000";
			let drawDonutFour = new drawDonutChart(this.ctx);
		drawDonutFour.set(this.xCoordinate, this.yCoordinate, 400, 0, Math.PI * 2, this.ringWidth, "#fff");
		drawDonutFour.draw(this.ringData);

		this.ctx.beginPath();
		this.ctx.arc(this.xCoordinate, this.yCoordinate, 540, 0, 50 * Math.PI);
		this.ctx.lineWidth = 3;
		this.ctx.strokeStyle = this.outlineColor;
		this.ctx.stroke();
		this.ctx.beginPath();
		this.ctx.arc(this.xCoordinate, this.yCoordinate, 500, 0, 50 * Math.PI);
		this.ctx.lineWidth = 3;
		this.ctx.strokeStyle = this.outlineColor;
		this.ctx.stroke();
		this.ctx.beginPath();
		this.ctx.arc(this.xCoordinate, this.yCoordinate, 460, 0, 50 * Math.PI);
		this.ctx.lineWidth = 3;
		this.ctx.strokeStyle = this.outlineColor;
		this.ctx.stroke();
		this.ctx.beginPath();
		this.ctx.arc(this.xCoordinate, this.yCoordinate, 420, 0, 50 * Math.PI);
		this.ctx.lineWidth = 3;
		this.ctx.strokeStyle =this.outlineColor;
		this.ctx.stroke();
		this.ctx.beginPath();
		this.ctx.arc(this.xCoordinate, this.yCoordinate, 380, 0, 50 * Math.PI);
		this.ctx.lineWidth = 3;
		this.ctx.strokeStyle = this.outlineColor;
		this.ctx.stroke();
		this.ctx.beginPath();
		this.ctx.arc(this.xCoordinate, this.yCoordinate, 340, 0, 50 * Math.PI);
		this.ctx.lineWidth = 3;
		this.ctx.strokeStyle = this.outlineColor;
		this.ctx.stroke();
		this.ctx.beginPath();
		this.ctx.arc(this.xCoordinate, this.yCoordinate, 300, 0, 50 * Math.PI);
		this.ctx.lineWidth = 3;
		this.ctx.strokeStyle = this.outlineColor;
		this.ctx.stroke();
		this.ctx.beginPath();
		this.ctx.arc(this.xCoordinate, this.yCoordinate, 260, 0, 50 * Math.PI);
		this.ctx.lineWidth = 3;
		this.ctx.strokeStyle = this.outlineColor;
		this.ctx.stroke();
		this.ctx.beginPath();
		this.ctx.arc(this.xCoordinate, this.yCoordinate, 220, 0, 50 * Math.PI);
		this.ctx.lineWidth = 3;
		this.ctx.strokeStyle = this.outlineColor;
		this.ctx.stroke();
		this.ctx.beginPath();
		this.ctx.arc(this.xCoordinate, this.yCoordinate, 180, 0, 50 * Math.PI);
		this.ctx.lineWidth = 3;
		this.ctx.strokeStyle = this.outlineColor;
		this.ctx.stroke();

		drawLine(this.ctx, 590, 50, 588, 410, this.outlineColor);
		drawLine(this.ctx, 317, 122, 498, 432, this.outlineColor);
		drawLine(this.ctx, 120, 320, 430, 500, this.outlineColor);
		drawLine(this.ctx, 48, 590, 408, 590, this.outlineColor);
		drawLine(this.ctx, 120, 860, 430, 680, this.outlineColor);
		drawLine(this.ctx, 317, 1056, 498, 745, this.outlineColor);
		drawLine(this.ctx, 588, 1128, 586, 768, this.outlineColor);
		drawLine(this.ctx, 676, 746, 857, 1058,this.outlineColor);
		drawLine(this.ctx, 743, 680, 1056, 860,this.outlineColor);
		drawLine(this.ctx, 676, 746, 857, 1058, this.outlineColor);
		drawLine(this.ctx, 768, 590, 1126, 591, this.outlineColor);
		drawLine(this.ctx, 744, 500, 1055, 320, this.outlineColor);
		drawLine(this.ctx, 676, 434, 857, 122, this.outlineColor);

			this.currentNote = "E4";
			this.audioContext.resume();
		}
		if (this.pitchKey === "g" && this.octaveKey === "ArrowLeft") {
			this.oscillator.frequency.value = 369.99;

			this.ringData.colors.cs[9] = "#000000";
			let drawDonutFour = new drawDonutChart(this.ctx);
		drawDonutFour.set(this.xCoordinate, this.yCoordinate, 400, 0, Math.PI * 2, this.ringWidth, "#fff");
		drawDonutFour.draw(this.ringData);

		this.ctx.beginPath();
		this.ctx.arc(this.xCoordinate, this.yCoordinate, 540, 0, 50 * Math.PI);
		this.ctx.lineWidth = 3;
		this.ctx.strokeStyle = this.outlineColor;
		this.ctx.stroke();
		this.ctx.beginPath();
		this.ctx.arc(this.xCoordinate, this.yCoordinate, 500, 0, 50 * Math.PI);
		this.ctx.lineWidth = 3;
		this.ctx.strokeStyle = this.outlineColor;
		this.ctx.stroke();
		this.ctx.beginPath();
		this.ctx.arc(this.xCoordinate, this.yCoordinate, 460, 0, 50 * Math.PI);
		this.ctx.lineWidth = 3;
		this.ctx.strokeStyle = this.outlineColor;
		this.ctx.stroke();
		this.ctx.beginPath();
		this.ctx.arc(this.xCoordinate, this.yCoordinate, 420, 0, 50 * Math.PI);
		this.ctx.lineWidth = 3;
		this.ctx.strokeStyle =this.outlineColor;
		this.ctx.stroke();
		this.ctx.beginPath();
		this.ctx.arc(this.xCoordinate, this.yCoordinate, 380, 0, 50 * Math.PI);
		this.ctx.lineWidth = 3;
		this.ctx.strokeStyle = this.outlineColor;
		this.ctx.stroke();
		this.ctx.beginPath();
		this.ctx.arc(this.xCoordinate, this.yCoordinate, 340, 0, 50 * Math.PI);
		this.ctx.lineWidth = 3;
		this.ctx.strokeStyle = this.outlineColor;
		this.ctx.stroke();
		this.ctx.beginPath();
		this.ctx.arc(this.xCoordinate, this.yCoordinate, 300, 0, 50 * Math.PI);
		this.ctx.lineWidth = 3;
		this.ctx.strokeStyle = this.outlineColor;
		this.ctx.stroke();
		this.ctx.beginPath();
		this.ctx.arc(this.xCoordinate, this.yCoordinate, 260, 0, 50 * Math.PI);
		this.ctx.lineWidth = 3;
		this.ctx.strokeStyle = this.outlineColor;
		this.ctx.stroke();
		this.ctx.beginPath();
		this.ctx.arc(this.xCoordinate, this.yCoordinate, 220, 0, 50 * Math.PI);
		this.ctx.lineWidth = 3;
		this.ctx.strokeStyle = this.outlineColor;
		this.ctx.stroke();
		this.ctx.beginPath();
		this.ctx.arc(this.xCoordinate, this.yCoordinate, 180, 0, 50 * Math.PI);
		this.ctx.lineWidth = 3;
		this.ctx.strokeStyle = this.outlineColor;
		this.ctx.stroke();

		drawLine(this.ctx, 590, 50, 588, 410, this.outlineColor);
		drawLine(this.ctx, 317, 122, 498, 432, this.outlineColor);
		drawLine(this.ctx, 120, 320, 430, 500, this.outlineColor);
		drawLine(this.ctx, 48, 590, 408, 590, this.outlineColor);
		drawLine(this.ctx, 120, 860, 430, 680, this.outlineColor);
		drawLine(this.ctx, 317, 1056, 498, 745, this.outlineColor);
		drawLine(this.ctx, 588, 1128, 586, 768, this.outlineColor);
		drawLine(this.ctx, 676, 746, 857, 1058,this.outlineColor);
		drawLine(this.ctx, 743, 680, 1056, 860,this.outlineColor);
		drawLine(this.ctx, 676, 746, 857, 1058, this.outlineColor);
		drawLine(this.ctx, 768, 590, 1126, 591, this.outlineColor);
		drawLine(this.ctx, 744, 500, 1055, 320, this.outlineColor);
		drawLine(this.ctx, 676, 434, 857, 122, this.outlineColor);

			this.currentNote = "F#4";
			this.audioContext.resume();
		}
	}

	@HostListener("window:keyup", ["$event"])
	handleKeyUpEvent(event: KeyboardEvent) {
		if (this.checkLowerCaseAlphabet(event.key)) {
			this.pitchKey = "";
		}
		if (this.checkDirectionKeys(event.key)) {
			this.octaveKey = "";
		}
		this.audioContext.suspend();
		this.currentNote = "";
		this.ringData.colors.cs = [this.defaultColors[0],this.defaultColors[1],this.defaultColors[2],this.defaultColors[3],this.defaultColors[4],this.defaultColors[5],this.defaultColors[6],this.defaultColors[7],this.defaultColors[8],this.defaultColors[9],this.defaultColors[10],this.defaultColors[11]];
		let drawDonut = new drawDonutChart(this.ctx);
		drawDonut.set(this.xCoordinate, this.yCoordinate, 520, 0, Math.PI * 2, this.ringWidth, "#fff");
		drawDonut.draw(this.ringData);
		let drawDonutTwo = new drawDonutChart(this.ctx);
		drawDonutTwo.set(this.xCoordinate,this.yCoordinate, 480, 0, Math.PI * 2, this.ringWidth, "#fff");
		drawDonutTwo.draw(this.ringData);
		let drawDonutThree = new drawDonutChart(this.ctx);
		drawDonutThree.set(this.xCoordinate, this.yCoordinate, 440, 0, Math.PI * 2, this.ringWidth, "#fff");
		drawDonutThree.draw(this.ringData);
		let drawDonutFour = new drawDonutChart(this.ctx);
		drawDonutFour.set(this.xCoordinate, this.yCoordinate, 400, 0, Math.PI * 2, this.ringWidth, "#fff");
		drawDonutFour.draw(this.ringData);
		let drawDonutFive = new drawDonutChart(this.ctx);
		drawDonutFive.set(this.xCoordinate, this.yCoordinate, 360, 0, Math.PI * 2, this.ringWidth, "#fff");
		drawDonutFive.draw(this.ringData);
		let drawDonutSix = new drawDonutChart(this.ctx);
		drawDonutSix.set(this.xCoordinate, this.yCoordinate, 320, 0, Math.PI * 2, this.ringWidth, "#fff");
		drawDonutSix.draw(this.ringData);
		let drawDonutSeven = new drawDonutChart(this.ctx);
		drawDonutSeven.set(this.xCoordinate, this.yCoordinate, 280, 0, Math.PI * 2, this.ringWidth, "#fff");
		drawDonutSeven.draw(this.ringData);
		let drawDonutEight = new drawDonutChart(this.ctx);
		drawDonutEight.set(this.xCoordinate, this.yCoordinate, 240, 0, Math.PI * 2, this.ringWidth, "#fff");
		drawDonutEight.draw(this.ringData);
		let drawDonutNine = new drawDonutChart(this.ctx);
		drawDonutNine.set(this.xCoordinate, this.yCoordinate, 200, 0, Math.PI * 2, this.ringWidth, "#fff");
		drawDonutNine.draw(this.ringData);

		this.ctx.beginPath();
		this.ctx.arc(this.xCoordinate, this.yCoordinate, 540, 0, 50 * Math.PI);
		this.ctx.lineWidth = 3;
		this.ctx.strokeStyle = this.outlineColor;
		this.ctx.stroke();
		this.ctx.beginPath();
		this.ctx.arc(this.xCoordinate, this.yCoordinate, 500, 0, 50 * Math.PI);
		this.ctx.lineWidth = 3;
		this.ctx.strokeStyle = this.outlineColor;
		this.ctx.stroke();
		this.ctx.beginPath();
		this.ctx.arc(this.xCoordinate, this.yCoordinate, 460, 0, 50 * Math.PI);
		this.ctx.lineWidth = 3;
		this.ctx.strokeStyle = this.outlineColor;
		this.ctx.stroke();
		this.ctx.beginPath();
		this.ctx.arc(this.xCoordinate, this.yCoordinate, 420, 0, 50 * Math.PI);
		this.ctx.lineWidth = 3;
		this.ctx.strokeStyle =this.outlineColor;
		this.ctx.stroke();
		this.ctx.beginPath();
		this.ctx.arc(this.xCoordinate, this.yCoordinate, 380, 0, 50 * Math.PI);
		this.ctx.lineWidth = 3;
		this.ctx.strokeStyle = this.outlineColor;
		this.ctx.stroke();
		this.ctx.beginPath();
		this.ctx.arc(this.xCoordinate, this.yCoordinate, 340, 0, 50 * Math.PI);
		this.ctx.lineWidth = 3;
		this.ctx.strokeStyle = this.outlineColor;
		this.ctx.stroke();
		this.ctx.beginPath();
		this.ctx.arc(this.xCoordinate, this.yCoordinate, 300, 0, 50 * Math.PI);
		this.ctx.lineWidth = 3;
		this.ctx.strokeStyle = this.outlineColor;
		this.ctx.stroke();
		this.ctx.beginPath();
		this.ctx.arc(this.xCoordinate, this.yCoordinate, 260, 0, 50 * Math.PI);
		this.ctx.lineWidth = 3;
		this.ctx.strokeStyle = this.outlineColor;
		this.ctx.stroke();
		this.ctx.beginPath();
		this.ctx.arc(this.xCoordinate, this.yCoordinate, 220, 0, 50 * Math.PI);
		this.ctx.lineWidth = 3;
		this.ctx.strokeStyle = this.outlineColor;
		this.ctx.stroke();
		this.ctx.beginPath();
		this.ctx.arc(this.xCoordinate, this.yCoordinate, 180, 0, 50 * Math.PI);
		this.ctx.lineWidth = 3;
		this.ctx.strokeStyle = this.outlineColor;
		this.ctx.stroke();

		drawLine(this.ctx, 590, 50, 588, 410, this.outlineColor);
		drawLine(this.ctx, 317, 122, 498, 432, this.outlineColor);
		drawLine(this.ctx, 120, 320, 430, 500, this.outlineColor);
		drawLine(this.ctx, 48, 590, 408, 590, this.outlineColor);
		drawLine(this.ctx, 120, 860, 430, 680, this.outlineColor);
		drawLine(this.ctx, 317, 1056, 498, 745, this.outlineColor);
		drawLine(this.ctx, 588, 1128, 586, 768, this.outlineColor);
		drawLine(this.ctx, 676, 746, 857, 1058,this.outlineColor);
		drawLine(this.ctx, 743, 680, 1056, 860,this.outlineColor);
		drawLine(this.ctx, 676, 746, 857, 1058, this.outlineColor);
		drawLine(this.ctx, 768, 590, 1126, 591, this.outlineColor);
		drawLine(this.ctx, 744, 500, 1055, 320, this.outlineColor);
		drawLine(this.ctx, 676, 434, 857, 122, this.outlineColor);
	}

	createSquare(fillColor, x, y, w, h) {
		this.ctx.fillStyle = fillColor;
		this.ctx.fillRect(x, y, w, h);
	}

	checkLowerCaseAlphabet(key: string): boolean {
		for (let i = 0; i < this.lowercaseAlphabet.length; i++) {
			if (key === this.lowercaseAlphabet[i]) {
				return true;
			}
		}
		return false;
	}

	checkDirectionKeys(key: string): boolean {
		for (let i = 0; i < this.directionKeys.length; i++) {
			if (key === this.directionKeys[i]) {
				return true;
			}
		}
		return false;
	}

	ngOnInit(): void {
		this.audioContext = new this.AudioContext();
		this.oscillator = this.audioContext.createOscillator();
		this.ctx = this.canvas.nativeElement.getContext("2d");
		this.ctx.rotate(15* Math.PI / 180);
		this.ctx.translate(170, -190);
		this.oscillator.type = "triangle";
		this.oscillator.frequency.value = 440;
		this.oscillator.connect(this.audioContext.destination);
		this.oscillator.start();
		this.audioContext.suspend();









		let drawDonut = new drawDonutChart(this.ctx);
		drawDonut.set(this.xCoordinate, this.yCoordinate, 520, 0, Math.PI * 2, this.ringWidth, "#fff");
		drawDonut.draw(this.ringData);
		let drawDonutTwo = new drawDonutChart(this.ctx);
		drawDonutTwo.set(this.xCoordinate,this.yCoordinate, 480, 0, Math.PI * 2, this.ringWidth, "#fff");
		drawDonutTwo.draw(this.ringData);
		let drawDonutThree = new drawDonutChart(this.ctx);
		drawDonutThree.set(this.xCoordinate, this.yCoordinate, 440, 0, Math.PI * 2, this.ringWidth, "#fff");
		drawDonutThree.draw(this.ringData);
		let drawDonutFour = new drawDonutChart(this.ctx);
		drawDonutFour.set(this.xCoordinate, this.yCoordinate, 400, 0, Math.PI * 2, this.ringWidth, "#fff");
		drawDonutFour.draw(this.ringData);
		let drawDonutFive = new drawDonutChart(this.ctx);
		drawDonutFive.set(this.xCoordinate, this.yCoordinate, 360, 0, Math.PI * 2, this.ringWidth, "#fff");
		drawDonutFive.draw(this.ringData);
		let drawDonutSix = new drawDonutChart(this.ctx);
		drawDonutSix.set(this.xCoordinate, this.yCoordinate, 320, 0, Math.PI * 2, this.ringWidth, "#fff");
		drawDonutSix.draw(this.ringData);
		let drawDonutSeven = new drawDonutChart(this.ctx);
		drawDonutSeven.set(this.xCoordinate, this.yCoordinate, 280, 0, Math.PI * 2, this.ringWidth, "#fff");
		drawDonutSeven.draw(this.ringData);
		let drawDonutEight = new drawDonutChart(this.ctx);
		drawDonutEight.set(this.xCoordinate, this.yCoordinate, 240, 0, Math.PI * 2, this.ringWidth, "#fff");
		drawDonutEight.draw(this.ringData);
		let drawDonutNine = new drawDonutChart(this.ctx);
		drawDonutNine.set(this.xCoordinate, this.yCoordinate, 200, 0, Math.PI * 2, this.ringWidth, "#fff");
		drawDonutNine.draw(this.ringData);

		this.ctx.beginPath();
		this.ctx.arc(this.xCoordinate, this.yCoordinate, 540, 0, 50 * Math.PI);
		this.ctx.lineWidth = 3;
		this.ctx.strokeStyle = this.outlineColor;
		this.ctx.stroke();
		this.ctx.beginPath();
		this.ctx.arc(this.xCoordinate, this.yCoordinate, 500, 0, 50 * Math.PI);
		this.ctx.lineWidth = 3;
		this.ctx.strokeStyle = this.outlineColor;
		this.ctx.stroke();
		this.ctx.beginPath();
		this.ctx.arc(this.xCoordinate, this.yCoordinate, 460, 0, 50 * Math.PI);
		this.ctx.lineWidth = 3;
		this.ctx.strokeStyle = this.outlineColor;
		this.ctx.stroke();
		this.ctx.beginPath();
		this.ctx.arc(this.xCoordinate, this.yCoordinate, 420, 0, 50 * Math.PI);
		this.ctx.lineWidth = 3;
		this.ctx.strokeStyle =this.outlineColor;
		this.ctx.stroke();
		this.ctx.beginPath();
		this.ctx.arc(this.xCoordinate, this.yCoordinate, 380, 0, 50 * Math.PI);
		this.ctx.lineWidth = 3;
		this.ctx.strokeStyle = this.outlineColor;
		this.ctx.stroke();
		this.ctx.beginPath();
		this.ctx.arc(this.xCoordinate, this.yCoordinate, 340, 0, 50 * Math.PI);
		this.ctx.lineWidth = 3;
		this.ctx.strokeStyle = this.outlineColor;
		this.ctx.stroke();
		this.ctx.beginPath();
		this.ctx.arc(this.xCoordinate, this.yCoordinate, 300, 0, 50 * Math.PI);
		this.ctx.lineWidth = 3;
		this.ctx.strokeStyle = this.outlineColor;
		this.ctx.stroke();
		this.ctx.beginPath();
		this.ctx.arc(this.xCoordinate, this.yCoordinate, 260, 0, 50 * Math.PI);
		this.ctx.lineWidth = 3;
		this.ctx.strokeStyle = this.outlineColor;
		this.ctx.stroke();
		this.ctx.beginPath();
		this.ctx.arc(this.xCoordinate, this.yCoordinate, 220, 0, 50 * Math.PI);
		this.ctx.lineWidth = 3;
		this.ctx.strokeStyle = this.outlineColor;
		this.ctx.stroke();
		this.ctx.beginPath();
		this.ctx.arc(this.xCoordinate, this.yCoordinate, 180, 0, 50 * Math.PI);
		this.ctx.lineWidth = 3;
		this.ctx.strokeStyle = this.outlineColor;
		this.ctx.stroke();

		drawLine(this.ctx, 590, 50, 588, 410, this.outlineColor);
		drawLine(this.ctx, 317, 122, 498, 432, this.outlineColor);
		drawLine(this.ctx, 120, 320, 430, 500, this.outlineColor);
		drawLine(this.ctx, 48, 590, 408, 590, this.outlineColor);
		drawLine(this.ctx, 120, 860, 430, 680, this.outlineColor);
		drawLine(this.ctx, 317, 1056, 498, 745, this.outlineColor);
		drawLine(this.ctx, 588, 1128, 586, 768, this.outlineColor);
		drawLine(this.ctx, 676, 746, 857, 1058,this.outlineColor);
		drawLine(this.ctx, 743, 680, 1056, 860,this.outlineColor);
		drawLine(this.ctx, 676, 746, 857, 1058, this.outlineColor);
		drawLine(this.ctx, 768, 590, 1126, 591, this.outlineColor);
		drawLine(this.ctx, 744, 500, 1055, 320, this.outlineColor);
		drawLine(this.ctx, 676, 434, 857, 122, this.outlineColor);
	}
}
