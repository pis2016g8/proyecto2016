/**
 * 
 */
var BASE_URL = "http://servidorgrupo8.azurewebsites.net/Servidor/";
var DROPBOX_URL="https://content.dropboxapi.com/1/files_put/auto/";

function getDUrl(url)
{
	return DROPBOX_URL.concat(url);
}
function getUrl(url) 
{
	return BASE_URL.concat(url);
}