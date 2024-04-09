function goToPage(pageId) {
	const pages = ['loginPage', 'forgotUsernameForm', 'forgotPasswordForm', 'boardListPage', 'registerPage', 'profilePage', 'editProfilePage', 'boardDetailPage', 'boardEditPage', 'boardWritePage'];


	pages.forEach(page => {
		const element = document.getElementById(page);
		if (element) {
			element.style.display = 'none';
		}
	});

	const pageElement = document.getElementById(pageId);
	if (pageElement) {
		pageElement.style.display = 'block';
	}
}
function goToLogin() {
	goToPage('loginPage');
}

function goToRegister() {
	goToPage('registerPage');
}

function goToProfilePage() {
	goToPage('goToProfilePage');
}
