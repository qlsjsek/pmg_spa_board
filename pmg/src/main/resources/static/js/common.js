function goToPage(pageId) {
	const pages = ['loginPage', 'forgotUserIdForm', 'forgotPasswordForm', 'boardListPage', 'registerPage', 'profilePage', 
	'editProfilePage', 'boardDetailPage', 'boardEditPage', 'boardWritePage', 'boardQuestionListPage', 'boardFreeListPage','boardRestListPage'];


	pages.forEach(page => {
		const element = document.getElementById(page);
		if (element) {
			element.style.display = 'none';
			clearText();
		}
	});

	const pageElement = document.getElementById(pageId);
	if (pageElement) {
		pageElement.style.display = 'block';
	}
}
function clearText() {
	document.getElementById("userId").value = "";
	document.getElementById("userPassword").value = "";
	document.getElementById("confirmPassword").value = "";
	document.getElementById("userName").value = "";
	document.getElementById("userBirthDate").value = "";
	document.getElementById("userPhone").value = "";
	document.getElementById("userAddress").value = "";
	document.getElementById("userEmail").value = "";
	document.getElementById("loginUserId").value = "";
	document.getElementById("loginUserPassword").value = "";
}

function goToLogin() {
	goToPage('loginPage');
}

function goToRegister() {
	goToPage('registerPage');
}

function goToProfilePage() {
	goToPage('profilePage');
}

function goToFindId() {
	goToPage('forgotUserIdForm');
}

function goToFindPassword() {
	goToPage('forgotPasswordForm');
}

function goToBoardList() {
	goToPage('boardListPage');
}

function goToEditProfile() {
	goToPage('editProfilePage');
}
function goToWriteBoard() {
	goToPage('boardWritePage');
}
function goToBoardQuestionList() {
	goToPage('boardQuestionListPage');
}
function goToBoardFreeList() {
	goToPage('boardFreeListPage');
}
function goToBoardRestList() {
	goToPage('boardRestListPage');
}