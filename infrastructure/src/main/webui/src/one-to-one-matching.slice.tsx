import { createAsyncThunk, createSlice } from "@reduxjs/toolkit";
import { MatchedOneToOneLifecycle } from "./api/model";
import { findAStranger, cancelMatchMaking, leaveMatchMaking } from "./api/endpoints";
import { RootState } from "./app/store";

interface OneToOneMatchingState {
    matchedOneToOneLifecycle: MatchedOneToOneLifecycle | undefined;
    canFindAStranger: boolean;
    canCancel: boolean;
    canLeave: boolean;
};

export const findAStrangerFromEndpoint = createAsyncThunk('one-to-one-matching/findAStranger', (requestedBy: string) =>
    findAStranger(requestedBy)
        .then(response => response)
        .catch(err => err)
);

export const cancelMatchMakingFromEndpoint = createAsyncThunk('one-to-one-matching/cancelMatchMaking', (requestedBy: string) =>
    cancelMatchMaking(requestedBy)
        .then(response => response)
        .catch(err => err)
);

export const leaveMatchMakingFromEndpoint = createAsyncThunk('one-to-one-matching/leaveMatchMaking', (requestedBy: string) =>
    leaveMatchMaking(requestedBy)
        .then(response => response)
        .catch(err => err)
);

const initialState: OneToOneMatchingState = {
    matchedOneToOneLifecycle: undefined,
    canFindAStranger: true,
    canCancel: false,
    canLeave: false
};

const oneToOneMatchingSlice = createSlice({
    name: 'oneToOneMatching',
    initialState,
    reducers: {
    },
    extraReducers: (builder) => {
        builder
            .addCase(
                findAStrangerFromEndpoint.fulfilled, (state) => {
                    return {
                        ...state,
                        canFindAStranger: false,
                        canCancel: true,
                        canLeave: false
                    };
                }
            )
            .addCase(
                cancelMatchMakingFromEndpoint.fulfilled, (state) => {
                    return {
                        ...state,
                        canFindAStranger: true,
                        canCancel: false,
                        canLeave: false
                    }
                }
            )
            .addCase(
                leaveMatchMakingFromEndpoint.fulfilled, (state) => {
                    return {
                        ...state,
                        canFindAStranger: true,
                        canCancel: false,
                        canLeave: false
                    }
                }
            )
            .addDefaultCase(() => { })
    }
});

export const oneToOneMatchingReducer = oneToOneMatchingSlice.reducer;
export const selecOneToOneMatching = (state: RootState) => state.oneToOneMatching;
