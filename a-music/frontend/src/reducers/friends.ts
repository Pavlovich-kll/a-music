import { FRIENDS_ACTION_TYPE } from '../constants';
import { IActionType, IFriendsState } from './reducers';

const INITIAL_STATE: IFriendsState = {
  addedFriends: [],
  awaitingFriends: [],
  requestedFriends: [],
};

export default (state: IFriendsState = INITIAL_STATE, { type, payload }: IActionType): IFriendsState => {
  if (typeof payload === 'function' && type === FRIENDS_ACTION_TYPE) state = payload(state);
  return state;
};
