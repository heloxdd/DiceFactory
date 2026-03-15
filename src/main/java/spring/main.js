const container = document.getElementById("diceContainer");
let startDrag = null;

for (let i = 1; i < 7; i++) {

    const die = document.createElement("div");
    die.textContent = i;
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

    container.appendChild(die);
}
