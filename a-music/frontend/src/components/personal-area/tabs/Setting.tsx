import React, { useContext, useState } from 'react';
import { ThemeContext } from '../../../App';
import { useDispatch, useSelector } from 'react-redux';
import { deleteUser, signOut } from '../../../actions/user';
import { notification } from 'antd';
import { InputLabel, Select } from '@material-ui/core';
import { IStore } from '../../../store';

const openNotification = () => {
  notification.success({
    message: 'Your account has been deleted.',
    style: {
      width: 600,
      marginLeft: 335 - 600,
      marginTop: 100,
    },
    duration: 0,
  });
};

interface IOptionItem {
  id: number;
  cityName?: string;
  countryName?: string;
}

const OptionItem = (item: IOptionItem) => {
  return (
    <option key={item.id} value={item.cityName}>
      {item.cityName || item.countryName}
    </option>
  );
};

const Settings = () => {
  const cities = useSelector(({ content }: IStore) => content.cities);
  const countries = useSelector(({ content }: IStore) => content.countries);
  const sortedCities = cities.sort((a: any, b: any) => (a.cityName < b.cityName ? -1 : 1));
  const sortedCountries = countries.sort((a: any, b: any) => (a.countryName < b.countryName ? -1 : 1));
  const current = useSelector(({ user }: IStore) => user.current);
  const dispatch = useDispatch();
  const { handleUpdateTheme, theme } = useContext(ThemeContext);

  const [city, setCity] = useState(current?.city ?? []);
  const [country, setCountry] = useState(current?.country ?? []);

  const handleChangeCountry = (event: React.ChangeEvent<{ value: unknown }>) => {
    //TODO: добавить экшн по апдейту юзера, когда его изменят на бэке.
    setCountry(event.target.value as string[]);
  };

  const handleChangeCity = (event: React.ChangeEvent<{ value: unknown }>) => {
    //TODO: добавить экшн по апдейту юзера, когда его изменят на бэке.
    setCity(event.target.value as string[]);
  };

  const handleChangeTheme = (theme: string) => (): void => {
    localStorage.setItem('theme', theme);
    handleUpdateTheme(theme);
  };

  const handleDeleteUser = () => {
    deleteUser(current && current.id);
    dispatch(signOut());
    openNotification();
  };

  return (
    <div>
      <div className={'block-button'}>
        <span>Change theme: </span>
        <button onClick={handleChangeTheme(theme === 'light' ? 'dark' : 'light')} className="MuiButton-colored">
          {theme === 'light' ? 'Dark' : 'Light'}
        </button>
      </div>
      <div className={'block-button'}>
        <span>Delete account? </span>
        <button onClick={handleDeleteUser} className="MuiButton-colored">
          Delete
        </button>
      </div>
      <div className={'block-button'}>
        <InputLabel htmlFor="filled-age-native-simple">Country</InputLabel>
        <Select
          native
          value={country}
          onChange={handleChangeCountry}
          inputProps={{
            name: 'country',
            id: 'country',
          }}
        >
          <option aria-label="None" value="">
            Choose your country
          </option>
          {sortedCountries.map(OptionItem)}
        </Select>
      </div>
      <div className={'block-button'}>
        <InputLabel htmlFor="filled-age-native-simple">City</InputLabel>
        <Select
          native
          value={city}
          onChange={handleChangeCity}
          inputProps={{
            name: 'city',
            id: 'city',
          }}
        >
          <option aria-label="None" value="">
            Choose your city
          </option>
          {sortedCities.map(OptionItem)}
        </Select>
      </div>
    </div>
  );
};

export default Settings;
