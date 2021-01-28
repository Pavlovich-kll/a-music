import React from "react";
import Enzyme, { mount } from "enzyme";
import Adapter from "enzyme-adapter-react-16";
import { wait } from "@testing-library/react";
import SendMusic from "../send-music";

Enzyme.configure({ adapter: new Adapter() });

jest.mock("react-router-dom", () => ({
  useHistory: () => ({
    push: jest.fn(),
  }),
}));

describe("frontend/src/components/send-music/send-music.container.tsx", () => {
  let fn;
  let wrapper;
  let props;

  beforeEach(() => {
    jest.clearAllMocks();
    fn = jest.fn();
    wrapper = mount(<SendMusic uploadMusic={fn} {...props} />);
  });

  it("should render component", () => {
    expect(
      wrapper.find('[data-testid="input-author"]').find("input").length
    ).toBe(1);
    expect(
      wrapper.find('[data-testid="input-title"]').find("input").length
    ).toBe(1);
    expect(
      wrapper.find('[data-testid="input-album"]').find("input").length
    ).toBe(1);
    expect(wrapper.find('[data-testid="type-id"]').find("select").length).toBe(
      1
    );
    expect(
      wrapper.find('[data-testid="genre-test"]').find("button").length
    ).toBe(1);
    expect(
      wrapper.find('[data-testid="submit-btn"]').find("button").length
    ).toBe(1);
  });

  it("uploadMusic should be triggered on submit the form", async () => {
    const submit = wrapper.find('[data-testid="submit-btn"]').find("button");
    submit.simulate("submit");
    wrapper.update();
    await wait(() => {
      expect(fn).toHaveBeenCalledTimes(1);
      expect(fn).not.toHaveBeenCalledTimes(10);
    });
  });

  it("author input should return value", () => {
    const author = wrapper.find('[data-testid="input-author"]').find("input");
    const value = {
      preventDefault() {},
      target: {
        id: "author",
        name: "author",
        value: "author",
      },
    };
    author.simulate("change", value);
    wrapper.update();

    expect(author.instance().value).toEqual("author");
  });

  it("title input should return value", () => {
    const title = wrapper.find('[data-testid="input-title"]').find("input");
    const value = {
      preventDefault() {},
      target: {
        id: "title",
        name: "title",
        value: "title",
      },
    };
    title.simulate("change", value);
    wrapper.update();

    expect(title.instance().value).toEqual("title");
  });

  it("album input should return value", () => {
    const album = wrapper.find('[data-testid="input-album"]').find("input");
    const value = {
      preventDefault() {},
      target: {
        id: "album",
        name: "album",
        value: "album",
      },
    };
    album.simulate("change", value);
    wrapper.update();

    expect(album.instance().value).toEqual("album");
  });

  it("button genre should add dynamic input and button", async () => {
    const addGenre = wrapper.find('[data-testid="genre-test"]').find("button");
    addGenre.simulate("click");
    wrapper.update();

    expect(
      wrapper.find('[data-testid="genre-delete"]').find("button")
    ).toHaveLength(1);
  });

  it("should delete dynamic input and button", async () => {
    const addGenre = wrapper.find('[data-testid="genre-test"]').find("button");
    addGenre.simulate("click");
    wrapper.update();

    expect(
      wrapper.find('[data-testid="genre-delete"]').find("button")
    ).toHaveLength(1);

    const btnDelete = wrapper
      .find('[data-testid="genre-delete"]')
      .find("button");
    btnDelete.simulate("click");

    wrapper.update();

    expect(
      wrapper.find('[data-testid="genre-delete"]').find("button")
    ).toHaveLength(0);
  });
});
