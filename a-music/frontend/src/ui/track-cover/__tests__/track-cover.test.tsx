import React from 'react';
import TrackCover from '../index';
import { shallow } from 'enzyme';
import { BASE_URL } from '../../../constants';

let wrapper: any;

describe('TrackCover', () => {
  test(`should render`, () => {
    wrapper = shallow(<TrackCover />);
    expect(wrapper).toMatchSnapshot();
  });

  test('should display default values when author and title are not passed', () => {
    wrapper = shallow(<TrackCover />);
    expect(wrapper.find('.track').text()).toEqual('title - author');
  });

  test('should display title and author when they are passed', () => {
    wrapper = shallow(<TrackCover title="Poison" author="Alice Cooper" />);
    expect(wrapper.find('.track').text()).toEqual('Poison - Alice Cooper');
  });

  test('should display alt when img is not displayed', () => {
    wrapper = shallow(<TrackCover title="Poison" author="Alice Cooper" cover_id="0assda" />);
    expect(wrapper.find('img').prop('alt')).toBe('Poison');
  });

  test('src should have exact structure', () => {
    wrapper = shallow(<TrackCover cover_id="12mdkasdk2" />);
    expect(wrapper.find('img').prop('src')).toBe(`${BASE_URL}/12mdkasdk2`);
  });
});
