import React from "react";
import Gallery from "../index";
import Enzyme, { mount } from "enzyme";
import Adapter from "enzyme-adapter-react-16";
import { BrowserRouter as Router } from "react-router-dom";

Enzyme.configure({ adapter: new Adapter() });

jest.mock("react-redux", () => ({
  useSelector: jest.fn().mockImplementation((selector) =>
    selector({
      playList: {
        list: [
          {
            _id: "1",
            pic: "data",
            title: "qweqwe",
            track_count: 12234234,
            likes: 12,
          },
        ],
      },
    })
  ),
  useDispatch: jest.fn().mockImplementation(() => jest.fn()),
}));

let wrapper;

describe("Gallery tests", () => {
  const setState = jest.fn();
  const useStateSpy = jest.spyOn(React, "useState");
  useStateSpy.mockImplementation((init) => [init, setState]);

  beforeEach(() => {
    wrapper = mount(
      <Router>
        <Gallery />{" "}
      </Router>
    );
  });

  afterEach(() => {
    jest.clearAllMocks();
  });

  it("render correctly component", () => {
    expect(wrapper).toMatchSnapshot();
  });

  it("slider must be rendered", () => {
    expect(wrapper.find('[data-testid="main__div"]').length).toBe(1);
    expect(wrapper.find('[data-testid="img"]').length).toBe(1);
    expect(wrapper.find('[data-testid="title"]').length).toBe(1);
    expect(wrapper.find('[data-testid="track_count"]').length).toBe(1);
  });
});
