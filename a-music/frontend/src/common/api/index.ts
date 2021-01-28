import axios from 'axios';
import { toast } from 'react-toastify';

const JWT_TOKEN_KEY: string = 'JWT_TOKEN_KEY';
const USER_ID_KEY: string = 'USER_ID_KEY';

const onFullfiled = (response: any) => {
  return response.data;
};

const onRejected = (error: any) => {
  if (error.response && error.response.status === 401) {
    toast.error('Please authorize to proceed!');
    localStorage.removeItem(JWT_TOKEN_KEY);
    localStorage.removeItem(USER_ID_KEY);
  } else if (error.response && error.response.status === 500) {
    window.location.replace('/error/?error=500');
  } else toast.error(error);
  return Promise.reject(error);
};

const HTTP = axios.create({
  baseURL: process.env.API_URL,
  headers: {
    'Content-Type': 'application/json',
    'Access-Control-Allow-Origin': process.env.API_URL,
  },
});

HTTP.interceptors.request.use((config: any) => ({
  ...config,
  headers: {
    ...config.headers,
    Authorization: localStorage.getItem(JWT_TOKEN_KEY),
  },
}));

HTTP.interceptors.response.use(onFullfiled, onRejected);

export default HTTP;
