const p1Container = document.getElementById("p1DiceContainer");
const p2Container = document.getElementById("p2DiceContainer");
let p1screen = document.getElementById("p1Screen");
let p2screen = document.getElementById("p2Screen");
let resultText = document.getElementById("resultText");
let resultScreen = document.getElementById("resultScreen");
let p2DicePreview = document.getElementById("p2DicePreview");
let p1DicePreview = document.getElementById("p1DicePreview");
let startDrag = null;
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
}


for (let i = 0; i < 6; i++) {
    p1Dice.push(parseInt(p1Container.children[i].textContent));
    p2Dice.push(parseInt(p2Container.children[i].textContent));
}
p2DicePreview.textContent = ("Enemy's dice are: "+p2Dice.join(", ").toString());
p1DicePreview.textContent = ("Enemy's dice are: "+p1Dice.join(", ").toString());


function ready() {
    p1screen.style.display = "none";
    p2screen.style.display = "flex";
}

function run() {
    p2screen.style.display = "none";
    resultScreen.style.display = "block";
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
        showResults();
    })
}

function showResults() {
    resultText.textContent = results;
}