import { Component, OnInit } from '@angular/core';
import { HttpClient } from '@angular/common/http';


//CONSTS AND JAVASCRIPT NOT USED IN THE VIEW
const SIGN_OF_THE_CROSS: String = "In the name of the Father, and of the Son, and of the Holy Spirit. Amen.";
const APOSTLES_CREED: String = "I believe in God, the Father almighty creator of heaven and earth and in Jesus Christ, His only Son, our Lord, who was conceived by the Holy Spirit, born of the Virgin Mary, suffered under Pontius Pilate, was crucified, died, and was buried. He descended into hell. On the third day he rose again from the dead. He ascended into heaven and is seated at the right hand of God, the Father almighty. He will come again in glory to judge the living and the dead. I believe in the Holy Spirit, the Holy Catholic Church, the communion of saints, the forgiveness of sins, the resurrection of the body, and the life everlasting. Amen.";
const OUR_FATHER: String = "Our Father, who art in heaven hallowed be thy name; thy kingdom come; thy will be done on earth as it is in heaven. Give us this day our daily bread and forgive us our trespasses as we forgive those who trespass against us; and lead us not into temptation, but deliver us from evil. Amen.";
const HAIL_MARY: String = "Hail Mary, full of grace. The Lord is with thee. Blessed art thou amongst women, And blessed is the fruit of thy womb, Jesus. Holy Mary, Mother of God, Pray for us sinners, Now and at the hour of our death. Amen.";
const GLORY_BE: String = "Glory be to the Father, and to the Son, and to the Holy Spirit. As it was in the beginning, is now, and ever shall be, world without end. Amen.";
const OH_MY_JESUS: String = "O my Jesus, forgive us of our sins. Save us from the fires of hell. Lead all souls into heaven, especially those in most need of thy mercy. Amen.";
const HAIL_HOLY_QUEEN: String = "Hail Holy Queen, Mother of Mercy, our Life, our Sweetness, and our hope. To thee we cry, poor banished children of Eve. To thee we send up our sighs, mourning and weeping in this vale of tears. Turn then most gracious advocate, Thine eyes of mercy toward us, and after this, our exile, show unto us, the blessed fruit of thy womb, Jesus. O clement, O loving, O sweet Virgin Mary. Pray for us O Holy Mother of God, That we may be made worthy of the promises of Christ. Amen.";
const FINAL_PRAYER: String = "Let us pray. O God, whose only begotten Son, by His life, death, and resurrection, has purchased for us the rewards of eternal life, grant, we beseech Thee, that meditating upon these mysteries of the Most Holy Rosary of the Blessed Virgin Mary, we may imitate what they contain and obtain what they promise, through the same Christ Our Lord. Amen.";
const JOYFUL_MYSTERIES: Array<String> = ["The Annunciation", "The Visitation", "The Nativity", "The Presentation in the Temple", "The Finding in the Temple"];
const SORROWFUL_MYSTERIES: Array<String> = ["The Agony in the Garden", "The Scourging at the Pillar", "The Crowning with Thorns", "The Carrying of the Cross", "The Crucifixion"];
const GLORIOUS_MYSTERIES: Array<String> = ["The Resurrection", "The Ascension", "The Coming of the Holy Spirit", "The Assumption of the Blessed Virgin Mother", 'The Coronation of the Blessed Virgin Mother'];
const LUMINOUS_MYSTERIES: Array<String> = ["The Baptism of Jesus", "The Wedding of Cana", "The Proclamation of the Kingdom", "The Transfiguration", "The Institution of the Eucharist"];

let currentDay: any;
function getCurrentDay() {
  var xhr = new XMLHttpRequest();
  //https://cors-anywhere.herokuapp.com/
  xhr.open("GET", "http://calapi.inadiutorium.cz/api/v0/en/calendars/default/today");
  xhr.onreadystatechange = function () {
    if (xhr.readyState == 4 && xhr.status == 200) {
      currentDay = JSON.parse(xhr.response);
    }
  };
  xhr.send();
}

@Component({
  selector: 'app-rosary-app',
  templateUrl: './rosary-app.component.html',
  styleUrls: ['./rosary-app.component.css']
})

export class RosaryAppComponent implements OnInit {

  constructor(private http: HttpClient) { }

  currentText: String;
  nextButton: any;
  mysteryCount: number = 0;
  currentCount: number = 0;
  currentMystery: String;
  todaysMystery: Array<String>;
  numberAsAWord: String;

  signOfTheCross() {
    this.currentText = SIGN_OF_THE_CROSS;
  }

  apostlesCreed() {
    this.currentText = APOSTLES_CREED;
  }

  ourFather() {
    this.currentText = OUR_FATHER;
  }

  gloryBe() {
    this.currentText = GLORY_BE;
  }

  ohMyJesus() {
    this.currentText = OH_MY_JESUS;
  }

  hailHolyQueen() {
    this.currentText = HAIL_HOLY_QUEEN;
  }

  finalPrayer() {
    this.currentText = FINAL_PRAYER;
  }

  hailMary() {
    this.currentText = HAIL_MARY;
  }

  mystery() {
    switch (this.mysteryCount) {
      case 0: {
        this.numberAsAWord = "First";
        break;
      }
      case 1: {
        this.numberAsAWord = "Second";
        break;
      }
      case 2: {
        this.numberAsAWord = "Third";
        break;
      }
      case 3: {
        this.numberAsAWord = "Fourth";
        break;
      }
      case 4: {
        this.numberAsAWord = "Fifth";
        break;
      }
    }
    this.currentText = `The ${this.numberAsAWord} Mystery is: ${this.todaysMystery[this.mysteryCount]}`;
    this.mysteryCount++;
  }

  reloadCurrentDay() {
    getCurrentDay();
  }

  signOfTheCrossNumbers: Array<number> = [1, 80];
  apostlesCreedNumbers: Array<number> = [2];
  hailMaryNumbers: Array<number> = [4, 5, 6, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 24, 25, 26, 27, 28, 29, 30, 31, 32, 33, 38, 39, 40, 41, 42, 43, 44, 45, 46, 47, 52, 53, 54, 55, 56, 57, 58, 59, 60, 61, 66, 67, 68, 69, 70, 71, 72, 73, 74, 75];
  ourFatherNumbers: Array<number> = [3, 9, 23, 37, 51, 65];
  gloryBeNumbers: Array<number> = [7, 20, 34, 48, 62, 76];
  mysteryNumbers: Array<number> = [8, 22, 36, 50, 64];
  ohMyJesusNumbers: Array<number> = [21, 35, 49, 63, 77];
  hailHolyQueenNumbers: Array<number> = [78];
  finalPrayerNumbers: Array<number> = [79];

  checkSignOfTheCrossNumbers() {
    for (let i = 0; i <= this.signOfTheCrossNumbers.length; i++) {
      if (this.currentCount == this.signOfTheCrossNumbers[i]) {
        return this.signOfTheCrossNumbers[i];
      } else {
        continue;
      }
    }
  }

  checkApostlesCreedNumbers() {
    for (let i = 0; i <= this.apostlesCreedNumbers.length; i++) {
      if (this.currentCount == this.apostlesCreedNumbers[i]) {
        return this.apostlesCreedNumbers[i];
      } else {
        continue;
      }
    }
  }

  checkHailMaryNumbers() {
    for (let i = 0; i <= this.hailMaryNumbers.length; i++) {
      if (this.currentCount == this.hailMaryNumbers[i]) {
        return this.hailMaryNumbers[i];
      } else {
        continue;
      }
    }
  }

  checkOurFatherNumbers() {
    for (let i = 0; i <= this.ourFatherNumbers.length; i++) {
      if (this.currentCount == this.ourFatherNumbers[i]) {
        return this.ourFatherNumbers[i];
      } else {
        continue;
      }
    }
  }

  checkGloryBeNumbers() {
    for (let i = 0; i <= this.gloryBeNumbers.length; i++) {
      if (this.currentCount == this.gloryBeNumbers[i]) {
        return this.gloryBeNumbers[i];
      } else {
        continue;
      }
    }
  }

  checkMysteryNumbers() {
    for (let i = 0; i <= this.mysteryNumbers.length; i++) {
      if (this.currentCount == this.mysteryNumbers[i]) {
        return this.mysteryNumbers[i];
      } else {
        continue;
      }
    }
  }

  checkOhMyJesusNumbers() {
    for (let i = 0; i <= this.ohMyJesusNumbers.length; i++) {
      if (this.currentCount == this.ohMyJesusNumbers[i]) {
        return this.ohMyJesusNumbers[i];
      } else {
        continue;
      }
    }
  }

  checkHailHolyQueenNumbers() {
    for (let i = 0; i <= this.hailHolyQueenNumbers.length; i++) {
      if (this.currentCount == this.hailHolyQueenNumbers[i]) {
        return this.hailHolyQueenNumbers[i];
      } else {
        continue;
      }
    }
  }

  checkFinalPrayerNumbers() {
    for (let i = 0; i <= this.finalPrayerNumbers.length; i++) {
      if (this.currentCount == this.finalPrayerNumbers[i]) {
        return this.finalPrayerNumbers[i];
      } else {
        continue;
      }
    }
  }

  nextCount() {
    ++this.currentCount;
    if (this.currentCount == this.checkSignOfTheCrossNumbers()) {
      this.signOfTheCross();
    } else if (this.currentCount == this.checkApostlesCreedNumbers()) {
      this.apostlesCreed();
    } else if (this.currentCount == this.checkOurFatherNumbers()) {
      this.ourFather();
    } else if (this.currentCount == this.checkHailMaryNumbers()) {
      this.hailMary();
    } else if (this.currentCount == this.checkGloryBeNumbers()) {
      this.gloryBe();
    } else if (this.currentCount == this.checkMysteryNumbers()) {
      this.mystery();
    } else if (this.currentCount == this.checkHailHolyQueenNumbers()) {
      this.hailHolyQueen();
    } else if (this.currentCount == this.checkFinalPrayerNumbers()) {
      this.finalPrayer();
    } else if (this.currentCount == this.checkOhMyJesusNumbers()) {
      this.ohMyJesus();
    }
  }

  resetCount() {
    this.currentCount = 0;
  }

  previousCount() {
    --this.currentCount;
    if (this.currentCount == this.checkSignOfTheCrossNumbers()) {
      this.signOfTheCross();
    } else if (this.currentCount == this.checkApostlesCreedNumbers()) {
      this.apostlesCreed();
    } else if (this.currentCount == this.checkOurFatherNumbers()) {
      this.ourFather();
    } else if (this.currentCount == this.checkHailMaryNumbers()) {
      this.hailMary();
    } else if (this.currentCount == this.checkGloryBeNumbers()) {
      this.gloryBe();
    } else if (this.currentCount == this.checkMysteryNumbers()) {
      this.mystery();
    } else if (this.currentCount == this.checkHailHolyQueenNumbers()) {
      this.hailHolyQueen();
    } else if (this.currentCount == this.checkFinalPrayerNumbers()) {
      this.finalPrayer();
    } else if (this.currentCount == this.checkOhMyJesusNumbers()) {
      this.ohMyJesus();
    }
  }

  ngOnInit() {
    console.log(this.ourFatherNumbers.length);
    getCurrentDay();
    var date = new Date();
    switch (date.getDay()) {
      case 1: {
        this.todaysMystery = LUMINOUS_MYSTERIES;
        break;
      }
      case 2: {
        this.todaysMystery = SORROWFUL_MYSTERIES;
        break;
      }
      case 3: {
        this.todaysMystery = GLORIOUS_MYSTERIES;
        break;
      }
      case 4: {
        this.todaysMystery = LUMINOUS_MYSTERIES;
        break;
      }
      case 5: {
        this.todaysMystery = SORROWFUL_MYSTERIES;
        break;
      }
      case 6: {
        this.todaysMystery = JOYFUL_MYSTERIES;
        break;
      }
      case 0: {
        getCurrentDay();
        if (currentDay.season == "advent") {
          this.todaysMystery = JOYFUL_MYSTERIES;
        } else if (currentDay.season == "christmas") {
          this.todaysMystery = JOYFUL_MYSTERIES;
        } else if (currentDay.season == "lent") {
          this.todaysMystery = SORROWFUL_MYSTERIES;
        } else {
          this.todaysMystery = GLORIOUS_MYSTERIES;
        }
        break;
      }
    }
  }
}
