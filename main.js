const p1Container = document.getElementById("p1DiceContainer");
const p2Container = document.getElementById("p2DiceContainer");
let p1screen = document.getElementById("p1Screen");
let p2screen = document.getElementById("p2Screen");
let resultScreen = document.getElementById("resultScreen");
let resultText = document.getElementById("resultText");
let p2DicePreview = document.getElementById("p2DicePreview");
let p1DicePreview = document.getElementById("p1DicePreview");
let nextFrameButton = document.getElementById("nextFrameButton");
let prevFrameButton = document.getElementById("prevFrameButton");
let winText = document.getElementById("winText");
let rulesBox = document.getElementById("rulesBox");
let rulesButton = document.getElementById("rulesButton");
let startDrag = null;
let currentResult=-8;
let p1Dice = [];
let p2Dice = [];
let results;

for (let i = 1; i <= 12; i++) {
    const die = document.createElement("div");
    die.textContent = String(Math.ceil(Math.random() * 6));
    die.className = "die";
    die.draggable = true;

    die.ondragstart = function() {
        startDrag = die;
    }
    die.ondragover = function(event) {
        event.preventDefault();
    }
    die.ondrop = function() {
        let temp = startDrag.textContent;
        startDrag.textContent = die.textContent;
        die.textContent = temp;
    }

    if (i%2===0) {
        p1Container.appendChild(die);
    } else {
        p2Container.appendChild(die);
    }
} //create dice

for (let i = 0; i < 6; i++) {
    p1Dice.push(parseInt(p1Container.children[i].textContent));
    p2Dice.push(parseInt(p2Container.children[i].textContent));
}
p2DicePreview.textContent = ("Enemy's dice are: "+p2Dice.join(", ").toString());
p1DicePreview.textContent = ("Enemy's dice are: "+p1Dice.join(", ").toString()); //create opposing player dice views

function ready() {
    p1screen.style.display = "none";
    p2screen.style.display = "flex";
} //p1 ready

function run() {
    p2screen.style.display = "none";
    resultScreen.style.display = "flex";
    p1Dice = [];
    p2Dice = [];
    rulesButton.style.display = "none";
    rulesBox.style.display = "none";

    for (let i = 0; i < 6; i++) {
        p1Dice.push(parseInt(p1Container.children[i].textContent));
        p2Dice.push(parseInt(p2Container.children[i].textContent));
    }

    fetch("https://dicefactory-production.up.railway.app/api/play", {
        method: "POST",
        headers: {"Content-Type": "application/json"},
        body: JSON.stringify({
            player1: p1Dice,
            player2: p2Dice
        })
    })
    .then(response => response.json())
    .then(data => {
        results = data;
        nextResult();
    })
} //runs the game and runs showResults with the given data

function prevResult() { //processes a single result into graphics to visualize it
    if (currentResult>0) {
        resultText.innerHTML = "";
        resultText.style.display = "flex";
        currentResult-=8;
        nextFrameButton.style.display = "inline";
        winText.style.display = "none";
        for (let i = 0; i < 8; i++) {
            resultText.innerHTML += results[currentResult+i] + "<br>";
        }
        console.log(currentResult);
    }
    if (currentResult>=8) {
        prevFrameButton.style.display = "inline";
    } if (currentResult<8) {
        prevFrameButton.style.display = "none";
    }
}

function nextResult() { //processes a single result into graphics to visualize it
    if (currentResult<8) {
        prevFrameButton.style.display = "none";
    }

    if (currentResult < results.length-17) {
        resultText.innerHTML = "";
        currentResult+=8;
        for (let i = 0; i < 8; i++) {
            resultText.innerHTML += results[currentResult+i] + "<br>";
        }
        console.log(currentResult);
        console.log(results.length);
    } else {
        resultText.style.display = "none";
        winText.innerHTML = results[results.length - 3]+"<br>";
        winText.innerHTML += results[results.length - 2]+"<br>";
        winText.innerHTML += results[results.length - 1];
        winText.style.display = "flex";
        nextFrameButton.style.display = "none";
        currentResult+=8;
    }

    if (currentResult>=8) {
        prevFrameButton.style.display = "inline";
    }
}

function toggleRules() {
    if (rulesBox.style.display === "none") {
        rulesBox.style.display = "flex";
        console.log("worked");
    } else {
        rulesBox.style.display = "none";
        console.log("not worked");
    }
    console.log("toggled")
}