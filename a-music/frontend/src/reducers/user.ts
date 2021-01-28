import { IActionType, IUserState } from './reducers.d';
import { USER_ACTION } from '../constants';

const INITIAL_STATE: IUserState = {
  current: JSON.parse(localStorage.getItem('USER') || 'null'),
  other: JSON.parse(localStorage.getItem('USERS') || '[]'),
  notifications: [],
  notificationBody: [],
};

export default (state: IUserState = INITIAL_STATE, { type, payload }: IActionType): IUserState => {
  if (typeof payload === 'function' && type === USER_ACTION) {
    return payload(state);
  }

  return state;
};
