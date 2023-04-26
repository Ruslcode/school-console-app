const text = document.querySelector('.zoom-text');

text.addEventListener('mouseover', () => {
    text.style.fontSize = 'larger';
});

text.addEventListener('mouseout', () => {
    text.style.fontSize = 'initial';
});