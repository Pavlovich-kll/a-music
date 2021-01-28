import { find, propEq, pipe } from 'ramda';
import { toast } from 'react-toastify';
import jwtDecode from 'jwt-decode';
import { USER_ACTION } from '../constants';
import HTTP from '../common/api';
import { Dispatch } from 'react';
import { IDType, IUserState } from '../reducers/reducers';

const STUB_CODE: number = 9999;
const VERIFY_PHONE_ENDPOINT: string = '/user-service/verification/phone/check-phone-code';
const RESET_PASSWORD_ENDPOINT: string = '/user-service/reset-password/change-password';
const UPDATE_PASSWORD_ENDPOINT: string = '/user-service/users/update-password';
const SIGN_UP_ENDPOINT: string = '/user-service/users';
const SIGN_UP_PHONE_ENDPOINT: string = '/user-service/users/create-by-phone';
const LOGIN_ENDPOINT: string = '/user-service/auth';
const CHECK_PHONE_EXISTS: string = '/user-service/users/check-phone';
const DELETE_USER: string = '/user-service/delete-user';
const CITY_USER: string = '/user-service/cities';
const COUNTRY_USER: string = '/user-service/countries';
const Verification: string = 'Verification';
const JWT_TOKEN_KEY: string = 'JWT_TOKEN_KEY';
const USER_ID_KEY: string = 'USER_ID_KEY';
const USER_NOTIFICATIONS_ENDPOINT: string = '/user-service/notifications/user-notification';
const NOTIFICATIONS_ENDPOINT: string = '/user-service/notifications';

interface IUser {
  email: string;
  firstName: string;
  lastName: string;
  password: string;
  repeatPassword: string;
  username: string;
  phone: string;
  avatar?: string;
  dateOfBirth?: string | number;
}

interface ICheckPhoneResponse {
  exists: boolean;
  error?: string;
}

export const getAllNotificationsById = async (id: number) => await HTTP.get(`${USER_NOTIFICATIONS_ENDPOINT}/${id}`);

export const getAllBodyNotifications = async () => await HTTP.get(NOTIFICATIONS_ENDPOINT);

export const getNotifications = (userId: number) => async (dispatch: Dispatch<object>) => {
  const response: any = await getAllNotificationsById(userId);
  dispatch({
    type: USER_ACTION,
    payload: (state: IUserState): IUserState => ({
      ...state,
      notifications: response,
    }),
  });
};

export const getBodyNotifications = () => async (dispatch: Dispatch<object>) => {
  const response: any = await getAllBodyNotifications();
  dispatch({
    type: USER_ACTION,
    payload: (state: IUserState): IUserState => ({
      ...state,
      notificationBody: response,
    }),
  });
};

export const updateNotifications = (id: number, notification: any) => async (dispatch: Dispatch<object>) => {
  try {
    await HTTP.put(`${USER_NOTIFICATIONS_ENDPOINT}/${id}`, notification);
    dispatch({
      type: USER_ACTION,
      payload: (state: IUserState): IUserState => ({
        ...state,
        notifications: [...state.notifications, notification],
      }),
    });
  } catch (error) {
    toast(error.message);
  }
};

export const checkPhone = async (phone: string): Promise<ICheckPhoneResponse> => {
  try {
    return await HTTP.post(CHECK_PHONE_EXISTS, { phone });
  } catch (error) {
    return { error: error.message } as ICheckPhoneResponse;
  }
};

export const verifyPhone = async (phone: string, code: number = STUB_CODE): Promise<Error | boolean> => {
  const { token } = await HTTP.post(VERIFY_PHONE_ENDPOINT, {
    phone,
    code: code,
  });

  if (token) {
    localStorage.setItem(Verification, `Bearer ${token}`);
    return true;
  } else {
    throw new Error('Your number is not valid');
  }
};

export const resetPassword = async (password: string, repeatPassword: string) => {
  try {
    await HTTP.post(RESET_PASSWORD_ENDPOINT, {
      newPassword: password,
      repeatNewPassword: repeatPassword,
    });
  } catch (e) {
    toast.error('Change password is failed, check your internet connection');
  }
};

export const updatePassword = async (password: string, repeatPassword: string, oldPassword: string = null) => {
  try {
    await HTTP.patch(UPDATE_PASSWORD_ENDPOINT, {
      newPassword: password,
      oldPassword: oldPassword,
      repeatNewPassword: repeatPassword,
    });
  } catch (e) {
    toast.error('Change password is failed, check your internet connection');
  }
};

export const createUser = async (user: IUser): Promise<Error | void> => {
  try {
    await HTTP.post(SIGN_UP_PHONE_ENDPOINT, user);
  } catch (error) {
    throw new Error(JSON.stringify(error.response.data));
  }
};

const auth = async (username: string, password: string): Promise<Error | any> => {
  try {
    const { token } = await HTTP.post(LOGIN_ENDPOINT, { username, password });
    localStorage.setItem(JWT_TOKEN_KEY, `Bearer ${token}`);
    const { id, ...rest } = jwtDecode(token);
    localStorage.setItem(USER_ID_KEY, id);
    return { id, ...rest };
  } catch (error) {
    toast.error('Wrong login or password');
  }
};

export const getCurrentUser = () => async (dispatch: Dispatch<object>) => {
  try {
    const id = localStorage.getItem(USER_ID_KEY);
    if (!id) return;
    const user = await getProfileByID(id);

    dispatch({
      type: USER_ACTION,
      payload: (state: IUserState): IUserState => ({
        ...state,
        current: {
          id: user.id,
          username: user.username,
          firstName: user.firstName,
          lastName: user.lastName,
          email: user.email,
          phone: user.phone,
          friendsList: [] as number[],
          waitingList: [] as number[],
          favorite: [] as number[],
          //TODO: когда будет приходить массив с бека - взять из 'user.roles'
          roles: ['plain', 'manager'] as string[],
          //TODO: когда будет приходить с бека - взять из 'user.isManager'
          isManager: true,
          city: user.city || 'Odessa',
          country: user.country || 'Ukraine',
          avatar: user.avatar,
        },
      }),
    });
  } catch (error) {}
};

const getProfileByID = async (id: IDType): Promise<any> => {
  return HTTP.get(`${SIGN_UP_ENDPOINT}/${id}`);
};

export const getCity = () => HTTP.get(CITY_USER);
export const getCountry = () => HTTP.get(COUNTRY_USER);

export const signIn = ({ username, password }: { username: string; password: string }) => async (
  dispatch: Dispatch<object>
) => {
  try {
    const { id } = await auth(username, password);
    const user = await getProfileByID(id);
    const theme = localStorage.getItem('theme');
    if (!theme) localStorage.setItem('theme', 'light');
    dispatch({
      type: USER_ACTION,
      payload: (state: IUserState): IUserState => ({
        ...state,
        current: {
          id: user.id,
          username: user.username,
          firstName: user.firstName,
          lastName: user.lastName,
          email: user.email,
          phone: user.phone,
          friendsList: [] as number[],
          waitingList: [] as number[],
          favorite: [] as number[],
          //TODO: когда будет приходить массив с бека - взять из 'user.roles'
          roles: ['plain', 'manager'] as string[],
          //TODO: когда будет приходить с бека - взять из 'user.isManager'
          isManager: true,
          city: user.city || 'Odessa',
          country: user.country || 'Ukraine',
          avatar: user.avatar,
        },
      }),
    });
  } catch (error) {}
};

export const signOut = () => {
  localStorage.removeItem(Verification);
  localStorage.removeItem(JWT_TOKEN_KEY);
  localStorage.removeItem(USER_ID_KEY);
  return {
    type: USER_ACTION,
    payload: <IUserState>(state: IUserState): IUserState => {
      return {
        ...state,
        current: null,
      };
    },
  };
};

export const deleteUser = async (id: IDType): Promise<any> => {
  try {
    return await HTTP.post(DELETE_USER, id);
  } catch (error) {
    return { exists: false };
  }
};

export const addToWaitingList = (id: IDType) => ({
  type: USER_ACTION,
  payload: (state: IUserState): IUserState => {
    const current = {
      ...state.current,
      waitingList: Array.from(new Set([id, ...state.current.waitingList])),
    };

    const getDesiredUser = pipe(
      (userList: any) => find(propEq('id', id))(userList),
      (user: any) => ({
        ...user,
        waitingList: Array.from(new Set([current.id, ...user.waitingList])),
      })
    );

    const desiredUser = getDesiredUser(state.other);

    const other = [
      ...state.other.filter((user: any) => user.id !== current.id && user.id !== desiredUser.id),
      current,
      desiredUser,
    ];

    return {
      ...state,
      current,
      other,
    };
  },
});

export const updateUser = (id: IDType, values: any) => async (dispatch: Dispatch<object>) => {
  try {
    await HTTP.patch(`/user-service/users/update/${id}`, values);
    await dispatch(getCurrentUser());
  } catch (error) {
    throw new Error(JSON.stringify(error.response.data));
  }
};

export const addManagerRole = (role: string | string[]) => ({
  type: USER_ACTION,
  payload: (state: IUserState): IUserState => ({
    ...state,
    current: {
      ...state.current,
      roles: state.current.roles.concat(role),
    },
  }),
});

export const deleteManagerRole = (role: string | string[]) => ({
  type: USER_ACTION,
  payload: (state: IUserState): IUserState => ({
    ...state,
    current: {
      ...state.current,
      roles: state.current.roles.filter((item: string) => item !== role),
    },
  }),
});

//Как я понял - сейчас данный кункционал не актуален. Оставлю тут на будущее.
/*export const addToFavorite = (id: IDType) => ({
  type: USER_ACTION,
  payload: <IUserState>(state: any): IUserState => ({
    ...state,
    current: {
      ...state.current,
      favorite: Array.from(new Set([id, ...state.current.favorite])),
    },
  }),
});

export const removeFromFavorite = (id: IDType) => ({
  type: USER_ACTION,
  payload: <IUserState>(state: any): IUserState => ({
    ...state,
    current: {
      ...state.current,
      favorite: state.current.favorite.filter(
        (contentID: IDType) => contentID !== id
      ),
    },
  }),
});*/
////
