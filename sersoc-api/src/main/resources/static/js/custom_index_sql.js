const search = document.querySelector('#search');
const table = document.querySelector('table');
const tr = table.querySelectorAll('tbody tr'); // Updated selector to target tbody rows only

search.addEventListener('keyup', function () {
	const filter = search.value.toUpperCase();
	for (let i = 0; i < tr.length; i++) {
		let descriptionTd = tr[i].getElementsByTagName('td')[1];
		let keyTd = tr[i].getElementsByTagName('td')[2];

		if (descriptionTd || keyTd) {
			let descriptionText = descriptionTd ? (descriptionTd.textContent || descriptionTd.innerText).toUpperCase() : '';
			let keyText = keyTd ? (keyTd.textContent || keyTd.innerText).toUpperCase() : '';

			if (descriptionText.indexOf(filter) > -1 || keyText.indexOf(filter) > -1) {
				tr[i].style.display = '';
			} else {
				tr[i].style.display = 'none';
			}
		}
	}
});
