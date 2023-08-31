export function setCookie(name, value, days) {
    const expirationDate = new Date();
    expirationDate.setDate(expirationDate.getDate() + days);
    const cookieValue = encodeURIComponent(value) + (days ? `; expires=${expirationDate.toUTCString()}` : '');
  
    document.cookie = `${name}=${cookieValue}; path=/`;
}

export function getCookie(name) {
    const cookieValue = document.cookie
        .split(';')
        .find(cookie => cookie.trim().startsWith(`${name}=`));
  
    if (cookieValue) {
        return decodeURIComponent(cookieValue.split('=')[1]);
    }
  
    return null;
}

