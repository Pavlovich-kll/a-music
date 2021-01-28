import { IFriend } from './friends.d';

export interface IActionType {
  type: string;
  payload: <T>(state: T) => T;
}
export type IDType = string | number;

export interface IUser {
  id: IDType;
  username: string;
  firstName: string;
  lastName: string;
  email: string;
  phone: string;
  friendsList: (string | number)[];
  waitingList: (string | number)[];
  favorite: (string | number)[];
  roles: string[];
  isManager: boolean;
  city: string;
  country: string;
  avatar?: sting;
}

export interface IUserState {
  current: IUser | null;
  other: IUser[];
  notifications: Array<Object>;
  notificationBody: Array<Object>;
}

export interface ITrack {
  album: string;
  likes: number;
  genres: string[];
  _id: string;
  author: string;
  title: string;
  type: string;
  track_id: string;
  cover_id: string;
  createdAt: string;
  updatedAt: string;
  __v: number;
}

export interface IPlayList {
  createdAt: string;
  description: string;
  likes: number;
  pic: string;
  title: string;
  track_count: number;
  tracks: ITrack[];
  updatedAt: string;
  __v: number;
  _id: string;
}

export interface ICities {
  id: number;
  cityName: string;
}

export interface ICountries {
  id: number;
  countryName: string;
}

export interface IContentState {
  playLists: IPlayList[];
  audio: ITrack[];
  current: ITrack | null;
  currentPlayList: ITrack[];
  isPlay: boolean;
  isRandom: boolean;
  isLoop: boolean;
  createPlayListTracks: ITrack[];
  countries: ICities[];
  cities: ICountries[];
}

export interface IFriendsState {
  addedFriends: IFriend[];
  awaitingFriends: IFriend[];
  requestedFriends: IFriend[];
}
