const container = document.getElementById("diceContainer");


for (let i = 1; i < 7; i++) {
    let startDrag = null;
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
        die.textContent = startDrag.textContent;
    }

    container.appendChild(die);
}
