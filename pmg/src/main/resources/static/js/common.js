function goToRegister() {
	document.getElementById('loginPage').style.display = 'none';
	document.getElementById('forgotUsernameForm').style.display = 'none';
	document.getElementById('forgotPasswordForm').style.display = 'none';
	document.getElementById('registerPage').style.display = 'block';
}
function goToLogin() {
	document.getElementById('registerPage').style.display = 'none';
		document.getElementById('forgotUsernameForm').style.display = 'none';
	document.getElementById('forgotPasswordForm').style.display = 'none';
	document.getElementById('loginPage').style.display = 'block';
}
function goToFindId() {
	document.getElementById('loginPage').style.display = 'none';
	document.getElementById('forgotUsernameForm').style.display = 'block';
}
function goToFindPassword() {
	document.getElementById('loginPage').style.display = 'none';
	document.getElementById('forgotPasswordForm').style.display = 'block';
}
function goToEditProfile() {
	document.getElementById('profilePage').style.display = 'none';
	document.getElementById('editProfilePage').style.display = 'block';
}
function goToBoardDetail(boardId) {
	document.getElementById('boardListPage').style.display = 'none';
	document.getElementById('boardDetailPage').style.display = 'block';
}
function goToEditBoard() {
	document.getElementById('boardDetailPage').style.display = 'none';
	document.getElementById('boardEditPage').style.display = 'block';
}
function goToWriteBoard(){
	document.getElementById('boardListPage').style.display = 'none';
	document.getElementById('boardWritePage').style.display = 'block';
}
function cancelWriteBoard(){
	document.getElementById('boardWritePage').style.display = 'none';
	document.getElementById('boardListPage').style.display = 'block';
}