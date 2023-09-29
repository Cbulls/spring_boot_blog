// 파라미터로 받은 토큰을 로컬 스토리지에 저장
const token = searchParam('token');

if (token) { // 만약 파라미터로 받은 토큰이 있다면
    localStorage.setItem("access_token", token)
}

function searchParam(key){
    return new URLSearchParams(location.search).get(key);
}