const p1Container = document.getElementById("p1DiceContainer");
const p2Container = document.getElementById("p2DiceContainer");
let p1screen = document.getElementById("p1Screen");
let p2screen = document.getElementById("p2Screen");
let resultScreen = document.getElementById("resultScreen");
let resultText = document.getElementById("resultText");
let p2DicePreview = document.getElementById("p2DicePreview");
let p1DicePreview = document.getElementById("p1DicePreview");
let nextFrameButton = document.getElementById("nextFrameButton");
let startDrag = null;
let currentResult=0;
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

    for (let i = 0; i < 6; i++) {
        p1Dice.push(parseInt(p1Container.children[i].textContent));
        p2Dice.push(parseInt(p2Container.children[i].textContent));
    }

    fetch("http://localhost:8080/api/play", {
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

function nextResult() { //processes a single result into graphics to visualize it
    if (currentResult < results.length) {
        resultText.innerHTML = results[currentResult]+"<br>";
        resultText.innerHTML += results[currentResult+1]+"<br>";
        resultText.innerHTML += results[currentResult+2]+"<br>";
        resultText.innerHTML += results[currentResult+3]+"<br>";
        resultText.innerHTML += results[currentResult+4]+"<br>";
        resultText.innerHTML += results[currentResult+5]+"<br>";
        resultText.innerHTML += results[currentResult+6]+"<br>";
        resultText.innerHTML += results[currentResult+7]+"<br>";
        currentResult+=8;
        console.log(results[currentResult]);
    } else {
        resultText.style.display = "none";
        nextFrameButton.style.display = "none";
        console.log("results failed: none left");
    }
}