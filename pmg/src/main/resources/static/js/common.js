function goToPage(pageId) {
	const pages = ['loginPage', 'forgotUserIdForm', 'forgotPasswordForm', 'boardListPage', 'registerPage', 'profilePage',
		'editProfilePage', 'boardDetailPage', 'boardEditPage', 'boardWritePage', 'boardQuestionListPage', 'boardFreeListPage', 'boardRestListPage'];


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
		resetFormState();
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
	var userIdInput = document.getElementById('userId');
	userIdInput.disabled = false;
}
function resetFormState() {
	isPasswordConfirmed = false;
	var beforeUpdateUserPasswordInput = document.getElementById('beforeUpdateUserPassword');
	var updatePasswordInput = document.getElementById('updatePassword');
	var updateConfirmPasswordInput = document.getElementById('updateConfirmPassword');
	var updateUserPhoneInput = document.getElementById('updateUserPhone');
	var updateUserAddressInput = document.getElementById('updateUserAddress');
	var updateUserEmailInput = document.getElementById('updateUserEmail');
    if (beforeUpdateUserPasswordInput) {
        beforeUpdateUserPasswordInput.value = "";
        beforeUpdateUserPasswordInput.disabled = false;
    }
    if (updatePasswordInput) {
        updatePasswordInput.value = "";
        updatePasswordInput.disabled = true;
    }
    if (updateConfirmPasswordInput) {
        updateConfirmPasswordInput.value = "";
        updateConfirmPasswordInput.disabled = true;
    }
    if (updateUserPhoneInput) {
        updateUserPhoneInput.disabled = true;
    }
    if (updateUserAddressInput) {
        updateUserAddressInput.disabled = true;
    }
    if (updateUserEmailInput) {
        updateUserEmailInput.disabled = true;
    }
}
function goToLogin() {
	goToPage('loginPage');
}

function goToRegister() {
	goToPage('registerPage');
}

function goToProfilePage() {
	resetFormState();
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
	location.reload();
}

function goToEditProfile() {
	goToPage('editProfilePage');
}
function goToWriteBoard() {
	var loginUser = document.getElementById('boardCreateUserId');
	if (!loginUser || loginUser == null) {
		alert('로그인이 필요합니다');
	} else {
		goToPage('boardWritePage');
	}
}
function goToEditBoard() {
	goToPage('boardEditPage');
}

function goToBoardQuestionList() {
	goToPage('boardQuestionListPage');
	questionBoardByCategoryId();
}
function goToBoardFreeList() {
	goToPage('boardFreeListPage');
	freeBoardByCategoryId();
}
function goToBoardRestList() {
	goToPage('boardRestListPage');
	restBoardByCategoryId();
}
function goToBoardDetail1() {
	goToPage('boardDetailPage');
}