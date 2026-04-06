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
let rulesDisplay = document.getElementById("rulesDisplay");
let rulesButton = document.getElementById("rulesButton");
let localButton = document.getElementById("localButton");
let onlineButton = document.getElementById("onlineButton");
let icon = document.getElementById("icon");
let iconImg = Math.ceil(Math.random() * 6);
let startDrag = null;
let currentResult=-8;
let p1Dice = [];
let p2Dice = [];
let results;

icon.href = "images/"+iconImg+".svg";


for (let i = 1; i <= 12; i++) {
    const die = document.createElement("div");
    die.textContent = String(Math.ceil(Math.random() * 6));
    die.className = "die";
    die.draggable = true;

    die.ondragstart = function () {
        startDrag = die;
    }
    die.ondragover = function (event) {
        event.preventDefault();
    }
    die.ondrop = function () {
        if (startDrag !== die) {
            let temp = startDrag.textContent;
            startDrag.textContent = die.textContent;
            die.textContent = temp;

            renderDie(die);
            renderDie(startDrag);
        }
    }

    die.addEventListener("touchstart", function(_event) {
        startDrag = die;
    })
    die.addEventListener("touchmove", function(event) {
        event.preventDefault();
    })
    die.addEventListener("touchend", function(event) {
        let targets = document.elementsFromPoint(event.changedTouches[0].clientX, event.changedTouches[0].clientY);
        let target;

        for (i = 0; true; i++) {
            target = targets[i];
            if (target.tagName.toLowerCase() === 'div' && (p1Container.contains(target) || p2Container.contains(target))) {
                break;
            }
        }

        if (target !== startDrag) {
            let temp = startDrag.textContent;
            startDrag.textContent = target.textContent;
            target.textContent = temp;

            renderDie(target);
            renderDie(startDrag);
        }
    })

    renderDie(die);

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

function p1ready() {
    p1screen.style.display = "none";
    p2screen.style.display = "flex";
} //p1 ready

function p2ready() {
    p2screen.style.display = "none";
    resultScreen.style.display = "flex";
    p1Dice = [];
    p2Dice = [];
    rulesButton.style.display = "none";
    rulesDisplay.style.display = "none";

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
    if (rulesDisplay.style.display === "flex") {
        rulesDisplay.style.display = "none";
    } else {
        rulesDisplay.style.display = "flex";
    }
}

function renderDie(die) {
    let dieImage=document.createElement("img");
    dieImage.setAttribute("src", "images/"+die.textContent+".svg");
    die.appendChild(dieImage);
}

function playLocal() {
    p1screen.style.display = "flex";
    localButton.style.display = "none";
    onlineButton.style.display = "none";
}