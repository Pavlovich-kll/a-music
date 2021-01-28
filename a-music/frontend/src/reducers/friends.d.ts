import { IDType } from './reducers';

interface IFriendPlace {
  cityName: string;
  country: {
    countryName: string;
    id: IDType;
  };
  id: IDType;
}

export interface IFriend {
  avatar: string | null;
  city: IFriendPlace | null;
  dateOfBirth: string | null;
  email: string;
  firstName: string;
  id: IDType;
  lastName: string;
  phone: string;
  username: string;
}
