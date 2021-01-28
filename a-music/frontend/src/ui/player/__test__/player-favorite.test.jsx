import React from "react";
import Enzyme, {shallow} from "enzyme";
import Adapter from "enzyme-adapter-react-16";
import {FavoriteButton} from "../player-favorite";
import {useSelector} from 'react-redux';
import {fireEvent, render} from '@testing-library/react';
import '@testing-library/jest-dom/extend-expect';


Enzyme.configure({ adapter: new Adapter() });
jest.mock('react-redux');

describe('FavoriteButton', () => {
  let wrapper;
  const trackId = "test";
  beforeEach(() => {
    wrapper = shallow(<FavoriteButton />);
  });

  afterEach(() => {
    localStorage.clear();
  })

  it('should render', () => {
    useSelector.mockReturnValue(trackId);
    expect(wrapper.find('[data-testid="icon-button"]').length).toBe(1);
  });

  it('should the icon be changed after the click on it', () => {
    wrapper.simulate('click');
    expect(wrapper.find('[data-testid="star-icon"]').length).toBe(1);
  });

  it('should the star-icon be active', () => {
    localStorage.setItem('favoriteTracks', JSON.stringify([trackId]));
    useSelector.mockReturnValue(trackId);
    const {getByTestId} = render(<FavoriteButton />);
    expect(getByTestId('star-icon')).toBeInTheDocument();
  });

  it('should the track_id be in the localStorage', () => {
    const {getByTestId} = render(<FavoriteButton />);
    const iconButton = getByTestId('icon-button');
    fireEvent.click(iconButton);
    expect(JSON.parse(localStorage.getItem('favoriteTracks'))).toStrictEqual([trackId]);
  });

  it('should delete the track_id from the localStorage', () => {
    useSelector.mockReturnValue(trackId);
    localStorage.setItem('favoriteTracks', JSON.stringify([trackId]));
    const {getByTestId} = render(<FavoriteButton />);
    const iconButton = getByTestId('icon-button');
    fireEvent.click(iconButton);
    expect(JSON.parse(localStorage.getItem('favoriteTracks'))).toStrictEqual([]);
  });
});
