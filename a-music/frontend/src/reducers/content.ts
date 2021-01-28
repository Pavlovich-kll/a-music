import { IActionType, IContentState } from './reducers.d';
import { CONTENT_ACTION_TYPE } from '../constants';

const INITIAL_STATE: IContentState = {
  playLists: [],
  audio: [],
  current: null,
  currentPlayList: [],
  isPlay: false,
  isRandom: false,
  isLoop: false,
  createPlayListTracks: [],
  countries: [],
  cities: [],
};

export default (state: IContentState = INITIAL_STATE, { type, payload }: IActionType): IContentState => {
  if (typeof payload === 'function' && type === CONTENT_ACTION_TYPE) state = payload(state);
  return state;
};
