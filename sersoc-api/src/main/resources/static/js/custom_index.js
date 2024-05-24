const search = document.querySelector('#search');
const table = document.querySelector('table');
const tr = table.querySelectorAll('tr');

search.addEventListener('keyup', function () {
	const filter = search.value.toUpperCase(); // Move inside event listener
	for (let i = 0; i < tr.length; i++) {
		let td = tr[i].getElementsByTagName('td')[1];
		if (td) {
			let textValue = td.textContent || td.innerText;
			if (textValue.toUpperCase().indexOf(filter) > -1) {
				tr[i].style.display = '';
			} else {
				tr[i].style.display = 'none';
			}
		}
	}
});