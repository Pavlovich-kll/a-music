import React from "react";
import Enzyme, {mount} from "enzyme";
import Adapter from "enzyme-adapter-react-16";
import { wait } from "@testing-library/react";
import CreatePlaylist from '../create-playlist';
import {BrowserRouter} from 'react-router-dom';

Enzyme.configure({ adapter: new Adapter() });

describe("frontend/src/components/create-playlist/create-playlist.tsx", () => {
    let fn;
    let wrapper;
    let props;

    beforeEach(() => {
        jest.clearAllMocks();
        fn = jest.fn();
        wrapper = mount(
            <BrowserRouter>
                <CreatePlaylist uploadPlayList={fn} {...props} />
            </BrowserRouter>
        );
    });

    it("should render component", () => {
        expect(
            wrapper.find('[data-testid="input-title"]').find("input").length
        ).toBe(1);
        expect(
            wrapper.find('[data-testid="input-description"]').find("input").length
        ).toBe(1);
        expect(
            wrapper.find('[data-testid="submit-btn"]').find("button").length
        ).toBe(1);
    });

    it("uploadPlayList should be triggered on submit the form", async () => {
        const submit = wrapper.find('[data-testid="submit-btn"]').find("button");
        submit.simulate("submit");
        wrapper.update();
        await wait(() => {
            expect(fn).toHaveBeenCalledTimes(1);
            expect(fn).not.toHaveBeenCalledTimes(10);
        });
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

    it("description input should return value", () => {
        const album = wrapper.find('[data-testid="input-description"]').find("input");
        const value = {
            preventDefault() {},
            target: {
                id: "description",
                name: "description",
                value: "description",
            },
        };
        album.simulate("change", value);
        wrapper.update();

        expect(album.instance().value).toEqual("description");
    });
})
