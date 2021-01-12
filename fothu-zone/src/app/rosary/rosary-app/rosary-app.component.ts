import { Component, OnInit } from "@angular/core";

const SIGN_OF_THE_CROSS: string = "In the name of the Father, and of the Son, and of the Holy Spirit. Amen.";
const APOSTLES_CREED: string =
	"I believe in God, the Father almighty creator of heaven and earth and in Jesus Christ, His only Son, our Lord, who was conceived by the Holy Spirit, born of the Virgin Mary, suffered under Pontius Pilate, was crucified, died, and was buried. He descended into hell. On the third day he rose again from the dead. He ascended into heaven and is seated at the right hand of God, the Father almighty. He will come again in glory to judge the living and the dead. I believe in the Holy Spirit, the Holy Catholic Church, the communion of saints, the forgiveness of sins, the resurrection of the body, and the life everlasting. Amen.";
const OUR_FATHER: string = "Our Father, who art in heaven hallowed be thy name; thy kingdom come; thy will be done on earth as it is in heaven. Give us this day our daily bread and forgive us our trespasses as we forgive those who trespass against us; and lead us not into temptation, but deliver us from evil. Amen.";
const HAIL_MARY: string = "Hail Mary, full of grace. The Lord is with thee. Blessed art thou amongst women, And blessed is the fruit of thy womb, Jesus. Holy Mary, Mother of God, Pray for us sinners, Now and at the hour of our death. Amen.";
const GLORY_BE: string = "Glory be to the Father, and to the Son, and to the Holy Spirit. As it was in the beginning, is now, and ever shall be, world without end. Amen.";
const OH_MY_JESUS: string = "O my Jesus, forgive us of our sins. Save us from the fires of hell. Lead all souls into heaven, especially those in most need of thy mercy.";
const HAIL_HOLY_QUEEN: string =
	"Hail Holy Queen, Mother of Mercy, hail our Life, our Sweetness, and our hope. To thee we cry, poor banished children of Eve. To thee we send up our sighs, mourning and weeping in this valley of tears. Turn then most gracious advocate, Thine eyes of mercy toward us, and after this, our exile, show unto us, the blessed fruit of thy womb, Jesus. O clement, O loving, O sweet Virgin Mary. Pray for us O Holy Mother of God, That we may be made worthy of the promises of Christ.";
const FINAL_PRAYER: string = "Let us pray. O God, whose only begotten Son, by His life, death, and resurrection, has purchased for us the rewards of eternal life, grant, we beseech Thee, that meditating upon these mysteries of the Most Holy Rosary of the Blessed Virgin Mary, we may imitate what they contain and obtain what they promise, through the same Christ Our Lord. Amen.";
const JOYFUL_MYSTERIES: Array<string> = ["The Annunciation", "The Visitation", "The Nativity", "The Presentation in the Temple", "The Finding in the Temple"];
const SORROWFUL_MYSTERIES: Array<string> = ["The Agony in the Garden", "The Scourging at the Pillar", "The Crowning with Thorns", "The Carrying of the Cross", "The Crucifixion"];
const GLORIOUS_MYSTERIES: Array<string> = ["The Resurrection", "The Ascension", "The Coming of the Holy Spirit", "The Assumption of the Blessed Virgin Mother", "The Coronation of the Blessed Virgin Mother"];
const LUMINOUS_MYSTERIES: Array<string> = ["The Baptism of Jesus", "The Wedding of Cana", "The Proclamation of the Kingdom", "The Transfiguration", "The Institution of the Eucharist"];

const SIGN_OF_THE_CROSS_ESP: string = "En el nombre del Padre, y del Hijo, y del Espíritu Santo. Amén.";
const APOSTLES_CREED_ESP: string =
	"Creo en Dios, Padre todopoderoso, Creador del Cielo y la tierra; creo en Jesucristo, Su único Hijo Nuestro Señor, quien fue concebido por obra y gracia del Espíritu Santo, nació de Santa María Virgen, padeció bajo el poder de Poncio Pilatos, fue crucificado, muerto y sepultado. Descendió a los infiernos, al tercer día resucitó entre los muertos, subió al Cielo y está sentado a la derecho de Dios Padre, todopoderoso; desde allí va a venir a juzgar a vivos y muertos. Creo en el Espíritu Santo, en la Santa Iglesia Católica, la comunión de los santos, el perdón de los pecados, la resurrección de la carne y la vida eterna. Amén.";
const OUR_FATHER_ESP: string = "Padre Nuestro, que estás en en cielo, santificado sea Tu nombre. Venga a nosotros tu Reino. Hágase Señor tu Voluntad, hacia en la Tierra como en el Cielo. Danos hoy nuestro Pan de cada día, perdona nuestras ofensas, como también nosotros perdonamos a los que nos ofenden. No nos dejes caer en tentación, y líbranos de todo mal. Amén.";
const HAIL_MARY_ESP: string = "Dios te salve María, llena eres de Gracia, el Señor es contigo. Bendita eres entre todas las mujeres, y bendito es el fruto de tu vientre, Jesús. Santa María, Madre de Dios, ruega Señora por nosotros los pecadores ahora y en la hora de nuestra muerte. Amén.";
const GLORY_BE_ESP: string = "Gloria al Padre, y al Hijo, y al Espíritu Santo. Como era en un principio, ahora y siempre, y por los siglos de los siglos. Amén.";
const OH_MY_JESUS_ESP: string = "Oh Jesús mío, perdona nuestros pecados, líbranos del fuego del infierno, lleva al cielo a todas las almas, especialmente a las más necesitadas de tu misericordia.";
const HAIL_HOLY_QUEEN_ESP: string =
	"Dios te salve, Reina y Madre de misericordia, vida, dulzura y esperanza nuestra. Dios te salve. A ti clamamos los desterrados hijos de Eva. A ti suspiramos gimiendo y llorando en este valle de lágrimas. Ea, pues, Señora, abogada nuestra: vuelve a nosotros esos tus ojos misericordiosos. Y después de este destierro, muéstranos a Jesús, fruto bendito de tu vientre. ¡Oh clemente, oh piadosa, oh dulce siempre Virgen María! Ruega por nosotros, Santa Madre de Dios, para que seamos dignos de las promesas de Jesucristo Nuestro Señor.";
const FINAL_PRAYER_ESP: string = "Oh Dios de quién Único Hijo nos ha otorgado los beneficios de la vida eterna, concédenos la gracia que te pedimos mientras meditamos los Misterios del Mas Santo Rosario de la Bienaventurada Virgen María, debemos imitar lo que contienen y obtener lo que prometen, a través del mismo Cristo Nuestro Señor. Amén.";
const JOYFUL_MYSTERIES_ESP: Array<string> = ["La Anunciación", "La Visitación", "La Natividad", "La Presentación de Jesús en el Templo", "El Reencuentro en el Templo"];
const SORROWFUL_MYSTERIES_ESP: Array<string> = ["La Agonía en el Huerto", "La Flagelación del Señor", "La Coronación con Espinas", "Jesus lleva la Cruz", "La Crucifixión"];
const GLORIOUS_MYSTERIES_ESP: Array<string> = ["La Resurrección del Señor", "La Ascensión del Señor", "La Venida del Espíritu Santo", "La Asunción de la Virgen María", "La Coronacion de la Virgen María"];
const LUMINOUS_MYSTERIES_ESP: Array<string> = ["Su Bautismo en el Jordán", "Su Autorrevelación en las Bodas de Caná", "Su Anuncio del Reino de Dios Invitando a la Conversión", "Su Transfiguración", "Institución de la Eucharistía"];

@Component({
	selector: "app-rosary-app",
	templateUrl: "./rosary-app.component.html",
	styleUrls: ["./rosary-app.component.css"],
})
export class RosaryAppComponent implements OnInit {
	constructor() {}

	currentEnglishText: string;
	currentSpanishText: string;
	mysteryCount: number = 0;
	currentCount: number = 0;
	currentMystery: string;
	todaysMystery: Array<string>;
	todaysESPMystery: Array<string>;
	englishNumberAsAWord: string;
	spanishNumberAsAWord: string;
	currentDay: any = 0;
	currentLanguage: string;
	luminousInactive: boolean = false;
	luminousActive: boolean = false;
	sorrowfulInactive: boolean = false;
	sorrowfulActive: boolean = false;
	gloriousInactive: boolean = false;
	gloriousActive: boolean = false;
	joyfulInactive: boolean = false;
	joyfulActive: boolean = false;
	englishInactive: boolean = false;
	englishActive: boolean = false;
	spanishInactive: boolean = false;
	spanishActive: boolean = false;

	signOfTheCrossNumbers: Array<number> = [1, 81];
	apostlesCreedNumbers: Array<number> = [2];
	hailMaryNumbers: Array<number> = [4, 5, 6, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 25, 26, 27, 28, 29, 30, 31, 32, 33, 34, 39, 40, 41, 42, 43, 44, 45, 46, 47, 48, 53, 54, 55, 56, 57, 58, 59, 60, 61, 62, 67, 68, 69, 70, 71, 72, 73, 74, 75, 76];
	ourFatherNumbers: Array<number> = [3, 10, 24, 38, 52, 66];
	gloryBeNumbers: Array<number> = [7, 21, 35, 49, 63, 77];
	mysteryNumbers: Array<number> = [9, 23, 37, 51, 65];
	ohMyJesusNumbers: Array<number> = [8, 22, 36, 50, 64, 78];
	hailHolyQueenNumbers: Array<number> = [79];
	finalPrayerNumbers: Array<number> = [80];
	plusOneMysteryNumbers: Array<number> = [10, 24, 38, 52, 66];

	async getCurrentDay() {
		let dayJson = await fetch("http://calapi.inadiutorium.cz/api/v0/en/calendars/default/today");
		this.currentDay = await dayJson.json();
	}

	setTodaysMysteries() {
		var date = new Date();
		switch (date.getDay()) {
			case 1: {
				this.setLuminousMysteries();
				break;
			}
			case 2: {
				this.setSorrowfulMysteries();
				break;
			}
			case 3: {
				this.setGloriousMysteries();
				break;
			}
			case 4: {
				this.setLuminousMysteries();
				break;
			}
			case 5: {
				this.setSorrowfulMysteries();
				break;
			}
			case 6: {
				this.setJoyfulMysteries();
				break;
			}
			case 0: {
				this.getCurrentDay();
				if (this.currentDay.season == "advent") {
					this.setJoyfulMysteries();
				} else if (this.currentDay.season == "christmas") {
					this.setJoyfulMysteries();
				} else if (this.currentDay.season == "lent") {
					this.setSorrowfulMysteries();
				} else {
					this.setGloriousMysteries();
				}
				break;
			}
		}
	}

	mystery() {
		switch (this.mysteryCount) {
			case 0: {
				this.englishNumberAsAWord = "First";
				this.spanishNumberAsAWord = "Primer";
				break;
			}
			case 1: {
				this.englishNumberAsAWord = "Second";
				this.spanishNumberAsAWord = "Segundo";
				break;
			}
			case 2: {
				this.englishNumberAsAWord = "Third";
				this.spanishNumberAsAWord = "Tercer";
				break;
			}
			case 3: {
				this.englishNumberAsAWord = "Fourth";
				this.spanishNumberAsAWord = "Cuarto";
				break;
			}
			case 4: {
				this.englishNumberAsAWord = "Fifth";
				this.spanishNumberAsAWord = "Quinto";
				break;
			}
		}
		this.currentEnglishText = `The ${this.englishNumberAsAWord} Mystery is: ${this.todaysMystery[this.mysteryCount]}.`;
		this.currentSpanishText = `El ${this.spanishNumberAsAWord} misterioso es: ${this.todaysESPMystery[this.mysteryCount]}.`;
	}

	reloadCurrentDay() {
		this.getCurrentDay();
	}

	nextCount() {
		if (this.mysteryNumbers.includes(this.currentCount)) {
			++this.mysteryCount;
		}
		++this.currentCount;
		this.changeText();
	}

	resetCount() {
		this.currentCount = 0;
		this.mysteryCount = 0;
	}

	previousCount() {
		if (this.mysteryNumbers.includes(this.currentCount)) {
			--this.mysteryCount;
		}
		--this.currentCount;
		this.changeText();
	}

	changeText() {
		if (this.hailMaryNumbers.includes(this.currentCount)) {
			this.currentEnglishText = HAIL_MARY;
			this.currentSpanishText = HAIL_MARY_ESP;
		} else if (this.signOfTheCrossNumbers.includes(this.currentCount)) {
			this.currentEnglishText = SIGN_OF_THE_CROSS;
			this.currentSpanishText = SIGN_OF_THE_CROSS_ESP;
		} else if (this.apostlesCreedNumbers.includes(this.currentCount)) {
			this.currentEnglishText = APOSTLES_CREED;
			this.currentSpanishText = APOSTLES_CREED_ESP;
		} else if (this.ourFatherNumbers.includes(this.currentCount)) {
			this.currentEnglishText = OUR_FATHER;
			this.currentSpanishText = OUR_FATHER_ESP;
		} else if (this.gloryBeNumbers.includes(this.currentCount)) {
			this.currentEnglishText = GLORY_BE;
			this.currentSpanishText = GLORY_BE_ESP;
		} else if (this.hailHolyQueenNumbers.includes(this.currentCount)) {
			this.currentEnglishText = HAIL_HOLY_QUEEN;
			this.currentSpanishText = HAIL_HOLY_QUEEN_ESP;
		} else if (this.finalPrayerNumbers.includes(this.currentCount)) {
			this.currentEnglishText = FINAL_PRAYER;
			this.currentSpanishText = FINAL_PRAYER_ESP;
		} else if (this.ohMyJesusNumbers.includes(this.currentCount)) {
			this.currentEnglishText = OH_MY_JESUS;
			this.currentSpanishText = OH_MY_JESUS_ESP;
		} else if (this.mysteryNumbers.includes(this.currentCount)) {
			this.mystery();
		}
	}

	setLuminousMysteries() {
		this.todaysMystery = LUMINOUS_MYSTERIES;
		this.todaysESPMystery = LUMINOUS_MYSTERIES_ESP;
		this.luminousActive = true;
		this.luminousInactive = false;
		this.sorrowfulActive = false;
		this.sorrowfulInactive = true;
		this.gloriousActive = false;
		this.gloriousInactive = true;
		this.joyfulActive = false;
		this.joyfulInactive = true;
	}

	changeToLuminousMysteries() {
		this.todaysMystery = LUMINOUS_MYSTERIES;
		this.todaysESPMystery = LUMINOUS_MYSTERIES_ESP;
		this.luminousActive = true;
		this.luminousInactive = false;
		this.sorrowfulActive = false;
		this.sorrowfulInactive = true;
		this.gloriousActive = false;
		this.gloriousInactive = true;
		this.joyfulActive = false;
		this.joyfulInactive = true;
		if (this.mysteryNumbers.includes(this.currentCount)) {
			this.mystery();
		}
	}

	setSorrowfulMysteries() {
		this.todaysMystery = SORROWFUL_MYSTERIES;
		this.todaysESPMystery = SORROWFUL_MYSTERIES_ESP;
		this.luminousActive = false;
		this.luminousInactive = true;
		this.sorrowfulActive = true;
		this.sorrowfulInactive = false;
		this.gloriousActive = false;
		this.gloriousInactive = true;
		this.joyfulActive = false;
		this.joyfulInactive = true;
	}

	changeToSorrowfulMysteries() {
		this.todaysMystery = SORROWFUL_MYSTERIES;
		this.todaysESPMystery = SORROWFUL_MYSTERIES_ESP;
		this.luminousActive = false;
		this.luminousInactive = true;
		this.sorrowfulActive = true;
		this.sorrowfulInactive = false;
		this.gloriousActive = false;
		this.gloriousInactive = true;
		this.joyfulActive = false;
		this.joyfulInactive = true;
		if (this.mysteryNumbers.includes(this.currentCount)) {
			this.mystery();
		}
	}

	setGloriousMysteries() {
		this.todaysMystery = GLORIOUS_MYSTERIES;
		this.todaysESPMystery = GLORIOUS_MYSTERIES_ESP;
		this.luminousActive = false;
		this.luminousInactive = true;
		this.sorrowfulActive = false;
		this.sorrowfulInactive = true;
		this.gloriousActive = true;
		this.gloriousInactive = false;
		this.joyfulActive = false;
		this.joyfulInactive = true;
	}

	changeToGloriousMysteries() {
		this.todaysMystery = GLORIOUS_MYSTERIES;
		this.todaysESPMystery = GLORIOUS_MYSTERIES_ESP;
		this.luminousActive = false;
		this.luminousInactive = true;
		this.sorrowfulActive = false;
		this.sorrowfulInactive = true;
		this.gloriousActive = true;
		this.gloriousInactive = false;
		this.joyfulActive = false;
		this.joyfulInactive = true;
		if (this.mysteryNumbers.includes(this.currentCount)) {
			this.mystery();
		}
	}

	setJoyfulMysteries() {
		this.todaysMystery = JOYFUL_MYSTERIES;
		this.todaysESPMystery = JOYFUL_MYSTERIES_ESP;
		this.luminousActive = false;
		this.luminousInactive = true;
		this.sorrowfulActive = false;
		this.sorrowfulInactive = true;
		this.gloriousActive = false;
		this.gloriousInactive = true;
		this.joyfulActive = true;
		this.joyfulInactive = false;
	}

	changeToJoyfulMysteries() {
		this.todaysMystery = JOYFUL_MYSTERIES;
		this.todaysESPMystery = JOYFUL_MYSTERIES_ESP;
		this.luminousActive = false;
		this.luminousInactive = true;
		this.sorrowfulActive = false;
		this.sorrowfulInactive = true;
		this.gloriousActive = false;
		this.gloriousInactive = true;
		this.joyfulActive = true;
		this.joyfulInactive = false;
		if (this.mysteryNumbers.includes(this.currentCount)) {
			this.mystery();
		}
	}

	changeToEnglish() {
		this.currentLanguage = "english";
		this.englishActive = true;
		this.englishInactive = false;
		this.spanishActive = false;
		this.spanishInactive = true;
	}

	changeToSpanish() {
		this.currentLanguage = "spanish";
		this.englishActive = false;
		this.englishInactive = true;
		this.spanishActive = true;
		this.spanishInactive = false;
	}

	ngOnInit() {
		this.changeToEnglish();
		this.setTodaysMysteries();
	}
}
