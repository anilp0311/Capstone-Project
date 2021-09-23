let stars = $('.fa-star');

function highlightTo(n) {
    stars.each(function() {
        if($(this).attr('data-value') <= n) {
            $(this).css('color', 'yellow');
        }
    });
}

stars.each(function() {

    // Highlight stars up to hovered one
    $(this).on('mouseenter', function() {
        let n = parseInt($(this).attr('data-value'));
        highlightTo(n);
    });

    // Clear highlighting
    $(this).on('mouseleave', function() {
        stars.each(function() { $(this).css('color', 'black'); });
    });

    // Highlight and send to backend
    $(this).on('click', function() {
        let n = parseInt($(this).attr('data-value'));
        highlightTo(n);

        // TODO Here - Make a fetch request to rating mapping
        let formData = new FormData();
        formData.append('rating', n);
        fetch('/users/rating/{id}', { method: 'POST', body: formData })
            .catch((err) => console.log(err));
    });
});