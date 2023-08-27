package amon.taser.service.impl

import amon.taser.model.AudioTranscription
import amon.taser.repository.TranscriptionRepository
import amon.taser.service.AudioTranscriptionApi
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import java.util.*

@Service
class FakeAudioTranscriptionApi (
        val audioTranscriptionRepository: TranscriptionRepository
): AudioTranscriptionApi {
    override fun startTranscription(audioFile: MultipartFile): UUID? {
        val audioTranscription = AudioTranscription(
                text = output,
                filename = audioFile.originalFilename!!,
                user = null,
                id = null
        )
        val aud = audioTranscriptionRepository.save(audioTranscription)
        aud.id?.let { transcribe(it) }
        return aud.id
    }

    private fun transcribe(audioFileID: UUID) {
        Thread.sleep(10000) // in non-fake implementation, this would be an async call to a transcription service
        val audioFile = audioTranscriptionRepository.findById(audioFileID).get()
        audioTranscriptionRepository.save(audioFile.copy(isCompleted = true))
    }
}

val output = "{'segments': [{'id': 0," +
        "   'start': 0.7003125," +
        "   'end': 63.627187500000005," +
        "   'speaker': 'J.T. Rick'," +
        "   'text': \" Hello everyone and welcome to the pre-recorded discussion of Flowers Foods third quarter 2022 results. This is J.T. Rick, SVP of Finance and Investor Relations. We released earnings on November 10th, 2022. Along with a transcript of these recorded remarks, you can find their earnings release and related slide presentation in the investor section of flowersfoods.com. We will host a live Q&A session on Friday, November 11th at 8.30 a.m. Eastern. Further details are posted in the investor section of our website. Before we get started, keep in mind that the information presented here may include forward-looking statements about the company's performance. Although we believe these statements to be reasonable, they are subject to risks and uncertainties that could cause actual results to differ materially. In addition to what you hear in these remarks, important factors relating to Flowers Foods business are fully detailed in our SEC filings. Providing remarks today are Riles McMullen, President and CEO, and Steve Kinsey, our CFO. Riles, I'll turn it over to you.\"," +
        "   'entities': {'ORG': [{'Flowers Foods': 2}," +
        "     {'Finance': 1}," +
        "     {'SEC': 1}," +
        "     {'CFO': 1}]," +
        "    'DATE': [{'quarter 2022': 1}," +
        "     {'November 10th, 2022': 1}," +
        "     {'Friday, November 11th': 1}," +
        "     {'today': 1}]," +
        "    'PERSON': [{'J.T. Rick': 1}," +
        "     {'Riles McMullen': 1}," +
        "     {'Steve Kinsey': 1}," +
        "     {'Riles': 1}]," +
        "    'TIME': [{'8.30 a.m. Eastern': 1}]}," +
        "   'sentiment': 'positive'," +
        "   'abstractive_summarization_text': 'Pre-recorded discussion of Flowers Foods third quarter 2022 results. Along with a transcript of these recorded remarks, you can find their earnings release and related slide presentation in investor section.Q&A to be held on Friday, November 11th at 8.30m. EasternGood day, ladies and gentlemen, and welcome to the call.'}," +
        "  {'id': 1," +
        "   'start': 64.3359375," +
        "   'end': 483.42656250000005," +
        "   'speaker': 'Riles McMullen, President and CEO'," +
        "   'text': \" Thanks JT, it's a pleasure to welcome everyone to our call. We continue to execute well in the third quarter of 2022, driving quarterly sales to record levels. I'm particularly proud of our performance given the unique challenges presented by the current environment. Recessionary economic forces and inflation remain significant factors, and I'll discuss those topics in more detail a little bit later in the call. Rest assured that those near-term issues will not distract us from our focus on the tremendous longer-term opportunities in front of us. Despite widespread inflationary pressures, the strong performance of our leading brands continues to underscore consumers' preference for differentiated products. As measured by IRI, Nature's Own grew sales dollars more than any other brand in the fresh packaged bread category and added 10 basis points of unit share in tracked channels, the most out of all major brands. Our premium brands, Dave's Killer Bread and Canyon Bakehouse, both maintain unit share in the fresh packaged bread category as measured in tracked channels, even with double-digit price increases meant to offset inflation. Such a performance in an environment where the private label bread category gains share and track channels highlights the strength of our product portfolio and the resilience of demand. Supply chain pressures, which affected results in the first half of the year, were less impactful in the third quarter. Our procurement team is doing extraordinary work amid heightened volatility, securing additional sources of supply, and mitigating cost increases when possible. Now I'll address our four strategic priorities, which we expect to drive our results in 2022 and beyond. Developing our team, focusing on our brands, prioritizing margins, and pursuing smart M&A. First, as always, I'd like to thank our Flowers team for their hard work and dedication, which has made our strong performance possible. Many of our team members were impacted by the destruction of Hurricane Ian, though I'm relieved to report that all made it through safely. As they have done countless times in similar situations, our team stepped up to support those affected by the hurricane, offering supplies and increasing production at sister bakeries to meet consumers' heightened demand. I'm proud of our response, which demonstrates the passion of our team and their commitment to serving our customers. Our second strategic priority is focusing on our brands. Our accelerated growth and learn-improved portfolio roles are driving continued growth. Tracked channel dollar sales of our leading brands in the quarter were strong, with Nature's Own up 13.2%, Dave's Killer Bread up 11.1%, and Canyon Bakehouse up 18.5%. Our pipeline of new and innovative products is performing even better than expected. Recent launches are delivering promising results and include DKB Epic Everything Breakfast Bread, Nature's Own Hawaiian Loaf, Canyon Bakehouse Brioche Rolls, and Nature's Own Perfectly Crafted Sourdough. Looking ahead, it's critical that we develop new sources of revenue outside of our core business. We are making substantial investments in our Agile Innovation Group, which is leveraging the power of our number one brands to extend our reach beyond the traditional bread aisle. This team, led by our Chief Marketing Officer, Debo Mukherjee, is dedicated to commercializing innovative products using an asset light model that enables quicker responsiveness to consumer insights without significant upfront capital expenditures. DKB Snack Bars are the first products developed with this new capability, and their success in test markets has encouraged us to expand distribution nationally beginning in January 2023. We're also testing a line of high-protein DKB snack bars in select markets and have a robust pipeline of additional new products planned. We could not be more excited about this new innovation capability and the prospect it holds for driving future growth. If you'd like to sample these new products before they become widely available in stores, we've launched a direct-to-consumer website at creationsbyflowersfoods.com. This new channel allows us to test products directly with consumers and gather feedback ahead of formal product launches. Another exciting development for one of our brands is a new three-year partnership with Wonder Bread and the Macy's Thanksgiving Day Parade. This is the first time Wunder is participating in the parade, and we're capitalizing on that event with a series of promotions on social media and in-store displays. The programs have already generated enthusiastic response from media and consumers, and we expect the partnership to further strengthen Wunder's national brand recognition. Our third strategic priority is margins, which remain a particular focus given the inflationary environment. The price increases we began implementing in June have served to mitigate much of the impact of higher input costs. Although higher costs are dilutive to margin percentages, I would note that our adjusted EBITDA increased on a dollar basis in the quarter, helped by those price increases. Price increases were particularly impactful in non-branded products, where we were focused on improving profitability. As we've highlighted previously, enhancing the contributions of our balanced growth and maximized profitability portfolio roles is a key part of our margin enhancement strategy. But pricing is only one part of our plan to drive margin expansion. Other measures include our portfolio strategy, which aims to shift our mix to a greater portion of higher margin branded retail products, along with cost savings and operational efficiency programs. We continue to expect significant benefits from operational efficiencies and procurement in 2022. Though we are revising our cost savings target from \$25 to \$35 million. to 20 to 30 million. You may recall that a portion of these savings were planned to come from our digital efforts. We believe the investments we're making in digital will lead to meaningful efficiency improvements and be a key driver of improved margins. However, as our ERP implementation progressed, it became clear that we needed to dedicate additional internal resources to ensure its success. At the same time, adoption of these new digital technologies at some of our bakeries was a bit slower than expected, and continued operational inefficiencies somewhat hampered our efforts. So while we remain excited about the long-term potential of digital, some of the cost savings benefits we expected in the second half of 2022 will be pushed into 2023 as we focus our resources on ERP implementation and improved operational performance. Meanwhile, our ERP program remains on track, and we are confident in our ability to implement it as planned. Our fourth priority is Smart M&A. We continue to monitor the deal market, actively seeking potential acquisitions that add capabilities, brands, or products to our robust existing lineup. We believe our strong balance sheet positions us well to act when we have financial, commercial, and operational conviction. As part of that process, in the third quarter, we incurred \$11.6 million in costs from the pursuit of a potential acquisition that failed to materialize. Although we are disappointed in the result, we remain committed to our disciplined approach as we consider additional acquisition targets. Now I'll turn it over to Steve to review the details of the quarter. And then I'll come back a little bit later to discuss our outlook for the current business environment. Steve.\"," +
        "   'entities': {'PERSON': [{'JT': 1}," +
        "     {'Dave': 2}," +
        "     {'Killer Bread': 2}," +
        "     {'Canyon Bakehouse': 1}," +
        "     {'Debo Mukherjee': 1}," +
        "     {'Wonder Bread': 1}," +
        "     {'Smart': 1}," +
        "     {'Steve': 2}]," +
        "    'DATE': [{'the third quarter of 2022': 1}," +
        "     {'quarterly': 1}," +
        "     {'the first half of the year': 1}," +
        "     {'the third quarter': 2}," +
        "     {'2022': 2}," +
        "     {'the quarter': 3}," +
        "     {'January 2023': 1}," +
        "     {'three-year': 1}," +
        "     {'June': 1}," +
        "     {'the second half of 2022': 1}," +
        "     {'2023': 1}]," +
        "    'ORG': [{'IRI': 1}," +
        "     {'Canyon Bakehouse Brioche Rolls': 1}," +
        "     {'Agile Innovation Group': 1}," +
        "     {'Macy': 1}," +
        "     {'ERP': 3}]," +
        "    'CARDINAL': [{'10': 1}," +
        "     {'four': 1}," +
        "     {'one': 2}," +
        "     {'only one': 1}," +
        "     {'20 to 30 million': 1}]," +
        "    'ORDINAL': [{'First': 1}," +
        "     {'second': 1}," +
        "     {'first': 2}," +
        "     {'third': 1}," +
        "     {'fourth': 1}]," +
        "    'NORP': [{'Flowers': 1}]," +
        "    'EVENT': [{'Hurricane Ian': 1}, {'Thanksgiving Day Parade': 1}]," +
        "    'WORK_OF_ART': [{\"Nature's Own\": 1}," +
        "     {\"Nature's Own Perfectly Crafted Sourdough\": 1}]," +
        "    'PERCENT': [{'13.2%': 1}, {'11.1%': 1}, {'18.5%': 1}]," +
        "    'MONEY': [{'\$25 to \$35 million': 1}, {'\$11.6 million': 1}]}," +
        "   'sentiment': 'positive'," +
        "   'abstractive_summarization_text': \"Here is the full text of the third-quarter earnings release:Nature's Own grew sales dollars more than any other brand in fresh packaged bread. Dave's Killer and Canyon Bakehouse maintained unit share in tracked channelsI'm pleased to report that we are on track to meet or exceed our full-year targets.I'm proud of our team's response to the hurricaneDKB Epic Everything Breakfast Bread, Nature's Own Hawaiian Loaf, Canyon Bakehouse Brioche Rolls are among recent launches.At Flowers Foods, we're the first in the country to develop, package and ship food products.We're making progress on three of our strategic priorities: margins, recognition and recognition.Price increases were particularly impactful in non-branded products.We are revising our cost savings target from \$25 to \$35 million.At first, we were excited about the potential of digital. However, adoption was a bit slower than expectedAcquisition costs were \$11.6 million in the third quarter.I'm going to give an update on our progress in getting back to full health.\"}," +
        "  {'id': 2," +
        "   'start': 484.35468750000007," +
        "   'end': 731.1009375000001," +
        "   'speaker': 'Steve'," +
        "   'text': \" Thank you, Riles, and hello, everyone. I'd like to echo your comments on our incredible team and express my sincere thanks for their outstanding efforts. As Riles mentioned, we are very pleased with our third quarter performance. Total sales increased 12.7% from the prior year period. Improved price mix drove the adjusted year-over-year increase up 17.8%, primarily due to price increases to mitigate inflationary pressures. Volume decreased 5.1%. mostly due to targeted sales rationalizations in cake and food service. Gross margin as a percentage of sales, excluding depreciation and amortization, decreased 310 basis points to 46.8%. Comparisons were impacted by higher ingredient and packaging cost. partly offset by higher sales that leveraged labor expenses and lower production volumes and outside purchases of product. Selling, distribution, and administrative expenses decreased 290 basis points as a percentage of sales to 38.6% in the third quarter. Results benefited from price increases that leveraged workforce-related expenses, lower employee fringe costs, and distributor distribution fees as a percent of sales. and decreased legal settlement and consulting costs, partly offset by acquisition-related costs. Excluding matters affecting comparability, adjusted SDNA expenses decreased 200 basis points to 36.4%. Gap diluted EPS for the quarter was 19 cents per share compared to 18 cents in the prior year period. Excluding the items affecting comparability detailed in the release, adjusted diluted EPS in the quarter was 30 cents per share, consistent with the prior year period. Turning now to our balance sheet liquidity and cash flow. Year to date, through the third quarter of fiscal 2022, cash flow from operating activities decreased by \$23.7 million to \$291.5 million. Capital expenditures increased \$41.6 million to \$128.4 million. largely due to the ongoing ERP upgrade, digital investments, and production capacity additions. Dividends paid increased \$8.5 million to \$140.1 million. In the second quarter, our Board of Directors increased the company's share repurchase authorization by 20 million shares. Year to date, we repurchased 34.6 million of common stock, including 18.1 million in the third quarter, leaving 24.4 million shares remaining for repurchase under the company's current share repurchase plan. Our financial position remains strong. At the end of the third quarter of fiscal 2022, net debt to trailing 12-month adjusted EBITDA stood at approximately 1.5 times. At quarter end, we held approximately \$173 million in cash and cash equivalents and had approximately \$692 million of remaining availability on our credit facilities. Now turning to our Outlook for 2022. Our updated sales guidance calls for an increase to the bottom end of the range. We now expect sales to be approximately \$4.807 billion to \$4.850 billion, representing an increase of 11% to 12% compared to prior guidance of 10% to 12%. Adjusted EPS is expected to be in the range of \$1.25 to \$1.30. implying a 9.9% compound annual growth rate off the 2019 base. The midpoint of this guidance exceeds our long-term financial targets of 1% to 2% sales growth. and 7% to 9% EPS growth off the 2019 base. At the end of the third quarter, virtually all our key commodities were covered for the remainder of 2022. To minimize volatility and provide adequate forewarning to allow for price adjustments, we have maintained our historical hedging strategy in which we attempt to increase the certainty of our key commodity costs six to 12 months out. Thank you, and now I'll turn it back to Riles.\"," +
        "   'entities': {'PERSON': [{'Riles': 3}]," +
        "    'ORDINAL': [{'third': 1}]," +
        "    'PERCENT': [{'12.7%': 1}," +
        "     {'17.8%': 1}," +
        "     {'5.1%': 1}," +
        "     {'46.8%': 1}," +
        "     {'38.6%': 1}," +
        "     {'36.4%': 1}," +
        "     {'11% to 12%': 1}," +
        "     {'10% to 12%': 1}," +
        "     {'9.9%': 1}," +
        "     {'1% to 2%': 1}," +
        "     {'7% to 9%': 1}]," +
        "    'DATE': [{'the prior year': 3}," +
        "     {'the third quarter': 2}," +
        "     {'the quarter': 2}," +
        "     {'Year to date': 2}," +
        "     {'the third quarter of fiscal 2022': 1}," +
        "     {'the second quarter': 1}," +
        "     {'the end of the third quarter of fiscal 2022': 1}," +
        "     {'12-month': 1}," +
        "     {'quarter end': 1}," +
        "     {'2022': 2}," +
        "     {'annual': 1}," +
        "     {'2019': 2}," +
        "     {'the end of the third quarter': 1}," +
        "     {'six to 12 months': 1}]," +
        "    'CARDINAL': [{'310': 1}," +
        "     {'290': 1}," +
        "     {'200': 1}," +
        "     {'20 million': 1}," +
        "     {'34.6 million': 1}," +
        "     {'18.1 million': 1}," +
        "     {'24.4 million': 1}," +
        "     {'approximately 1.5': 1}]," +
        "    'ORG': [{'EPS': 3}, {'ERP': 1}, {'Board of Directors': 1}]," +
        "    'MONEY': [{'19 cents': 1}," +
        "     {'18 cents': 1}," +
        "     {'30 cents': 1}," +
        "     {'\$23.7 million to \$291.5 million': 1}," +
        "     {'\$41.6 million to \$128.4 million': 1}," +
        "     {'\$8.5 million to \$140.1 million': 1}," +
        "     {'approximately \$173 million': 1}," +
        "     {'approximately \$692 million': 1}," +
        "     {'approximately \$4.807 billion to \$4.850 billion': 1}," +
        "     {'\$1.25 to \$': 1}," +
        "     {'1.30': 1}]," +
        "    'LOC': [{'Outlook': 1}]}," +
        "   'sentiment': 'positive'," +
        "   'abstractive_summarization_text': \"I would like to take this opportunity to thank you for your continued support. Third quarter adjusted earnings per share rose 17.8%Cost of sales rose 5.7% on higher ingredient, packaging cost.Selling, distribution, and administrative expenses decreased 290 basis points.Third-quarter diluted EPS was 30 cents, unchanged from prior year period.Third-quarter net sales rose 7.5% to \$1.3 billion.Earnings per share to be in the range of \$1.30 to \$1.35. Sales guidance calls for an increase to bottom end of rangemidpoint of guidance exceeds long-term financial targets.I've been meaning to write this for a long time, but it's taken me so long that I don't know where to start.\"}," +
        "  {'id': 3," +
        "   'start': 731.8265625," +
        "   'end': 867.1640625000001," +
        "   'speaker': 'Riles McMullen, President and CEO'," +
        "   'text': \" Thank you, Steve. You've just heard details on our strong financial results in the quarter. Now I'd like to address some of the key factors impacting the current environment, including inflationary pressures in the state of the US consumer. Inflation remains high, with the Consumer Price Index showing food and energy prices up 11% and 20% respectively over the last 12 months ended September. Those price increases are pressuring consumers and encouraging some to trade down to lower price products and shift more of their food purchases to value-oriented merchants such as mass, dollar, and club stores. As a result, in track channels measuring fresh packaged bread, overall private label sales gained 100 basis points of unit share in the third quarter. But as we noted on our last earnings call, that private label strength has been driven by concentrated activity in the mass merchandise channel where some private label retail prices have not adjusted to reflect inflationary pressures. In areas of retail where private label pricing generally reflects the higher cost, particularly in the grocery channel, brands are performing better. Our branded retail products gained unit share in tracked grocery channels for each quarter this year. At the same time, inflation is also pressuring the food service category. Consumers are eating more meals at home than prior to the pandemic, and that trend is expected to continue due to the cost advantages of eating in home compared to out of home. We expect to benefit from these trends as we focus our efforts on growing branded retail sales. Overall, demand elasticity has been in line with our expectations remaining below historical levels. The bread category has proven resilient over time, and that has been borne out even in the current environment. Our broad product lineup enables us to capture consumer demand at all price points and in a variety of distribution outlets. We are monitoring inflation and demand elasticity closely and remain confident that we will emerge from this period even stronger. We expect our relentless focus on developing new and innovative products for consumers and providing excellent service to our customers will continue to drive growth. The entire Flowers team remains committed to enhancing shareholder value, and we expect to continue to deliver results in line with or better than our long-term financial targets. Thank you very much for your time. This concludes our prepared remarks.\"," +
        "   'entities': {'PERSON': [{'Steve': 1}]," +
        "    'DATE': [{'the quarter': 1}," +
        "     {'the last 12 months ended September': 1}," +
        "     {'the third quarter': 1}," +
        "     {'each quarter this year': 1}]," +
        "    'GPE': [{'US': 1}]," +
        "    'PERCENT': [{'11% and 20%': 1}]," +
        "    'CARDINAL': [{'100': 1}]," +
        "    'NORP': [{'Flowers': 1}]}," +
        "   'sentiment': 'positive'," +
        "   'abstractive_summarization_text': 'Here is the full text of the earnings call:Private label sales gained 100 basis points of unit share in the third quarter.We expect to benefit from cost advantages of eating in home compared to out of home.Good morning and thank you for joining us for this conference.'," +
        "   'Sentiment-sentence-by-sentence': [[1, 0.4]," +
        "    [2, 0.0]," +
        "    [3, 0.0]," +
        "    [4, 0.0]," +
        "    [5, 0.0]," +
        "    [6, 0.136]," +
        "    [7, 0.0]," +
        "    [8, 0.0]," +
        "    [9, 0.011]," +
        "    [10, 0.233]," +
        "    [11, 0.0]," +
        "    [12, 0.0]," +
        "    [13, 0.2]," +
        "    [14, 0.8]," +
        "    [15, 0.0]," +
        "    [16, 0.392]," +
        "    [17, 0.178]," +
        "    [18, 0.333]," +
        "    [19, 0.433]," +
        "    [20, 0.306]," +
        "    [21, 0.3]," +
        "    [22, 0.0]," +
        "    [23, -0.021]," +
        "    [24, 0.167]," +
        "    [25, 0.214]," +
        "    [26, 0.098]," +
        "    [27, 0.5]," +
        "    [28, 0.0]," +
        "    [29, 0.8]," +
        "    [30, 0.0]," +
        "    [31, 0.0]," +
        "    [32, 0.517]," +
        "    [33, 0.259]," +
        "    [34, 0.417]," +
        "    [35, 0.045]," +
        "    [36, 0.25]," +
        "    [37, 0.438]," +
        "    [38, 0.197]," +
        "    [39, 0.136]," +
        "    [40, 0.253]," +
        "    [41, 0.268]," +
        "    [42, 0.118]," +
        "    [43, 0.062]," +
        "    [44, 0.011]," +
        "    [45, 0.3]," +
        "    [46, 0.083]," +
        "    [47, 0.225]," +
        "    [48, 0.25]," +
        "    [49, 0.167]," +
        "    [50, -0.083]," +
        "    [51, 0.0]," +
        "    [52, 0.208]," +
        "    [53, 0.375]," +
        "    [54, 0.0]," +
        "    [55, 0.167]," +
        "    [56, 0.133]," +
        "    [57, 0.009]," +
        "    [58, 0.018]," +
        "    [59, 0.5]," +
        "    [60, 0.107]," +
        "    [61, -0.067]," +
        "    [62, 0.144]," +
        "    [63, -0.167]," +
        "    [64, -0.75]," +
        "    [65, -0.047]," +
        "    [66, 0.0]," +
        "    [67, 0.0]," +
        "    [68, 0.525]," +
        "    [69, 0.325]," +
        "    [70, 0.0]," +
        "    [71, -0.125]," +
        "    [72, -0.4]," +
        "    [73, -0.125]," +
        "    [74, -0.2]," +
        "    [75, 0.167]," +
        "    [76, -0.2]," +
        "    [77, 0.0]," +
        "    [78, -0.1]," +
        "    [79, -0.4]," +
        "    [80, 0.0]," +
        "    [81, 0.217]," +
        "    [82, 0.0]," +
        "    [83, -0.2]," +
        "    [84, -0.062]," +
        "    [85, 0.0]," +
        "    [86, 0.0]," +
        "    [87, -0.1]," +
        "    [88, 0.217]," +
        "    [89, -0.133]," +
        "    [90, -0.4]," +
        "    [91, 0.0]," +
        "    [92, -0.2]," +
        "    [93, -0.45]," +
        "    [94, -0.4]," +
        "    [95, 0.0]," +
        "    [96, 0.111]," +
        "    [97, 0.0]," +
        "    [98, 0.0]," +
        "    [99, 0.217]," +
        "    [100, 0.0]," +
        "    [101, 0.053]," +
        "    [102, 0.115]," +
        "    [103, 0.075]," +
        "    [104, 0.0]," +
        "    [105, 0.193]," +
        "    [106, 0.5]," +
        "    [107, 0.0]," +
        "    [108, 0.069]," +
        "    [109, 0.0]," +
        "    [110, 0.0]," +
        "    [111, 0.0]," +
        "    [112, 0.062]," +
        "    [113, 0.5]," +
        "    [114, 0.545]," +
        "    [115, 0.167]," +
        "    [116, 0.26]," +
        "    [117, 0.0]]}]," +
        " 'Global_entities_list': {'ORG': [{'Flowers Foods': 2}," +
        "   {'Finance': 1}," +
        "   {'SEC': 1}," +
        "   {'CFO': 1}," +
        "   {'IRI': 1}," +
        "   {'Canyon Bakehouse Brioche Rolls': 1}," +
        "   {'Agile Innovation Group': 1}," +
        "   {'Macy': 1}," +
        "   {'ERP': 4}," +
        "   {'EPS': 3}," +
        "   {'Board of Directors': 1}]," +
        "  'DATE': [{'quarter 2022': 1}," +
        "   {'November 10th, 2022': 1}," +
        "   {'Friday, November 11th': 1}," +
        "   {'today': 1}," +
        "   {'the third quarter of 2022': 1}," +
        "   {'quarterly': 1}," +
        "   {'the first half of the year': 1}," +
        "   {'the third quarter': 5}," +
        "   {'2022': 4}," +
        "   {'the quarter': 6}," +
        "   {'January 2023': 1}," +
        "   {'three-year': 1}," +
        "   {'June': 1}," +
        "   {'the second half of 2022': 1}," +
        "   {'2023': 1}," +
        "   {'the prior year': 3}," +
        "   {'Year to date': 2}," +
        "   {'the third quarter of fiscal 2022': 1}," +
        "   {'the second quarter': 1}," +
        "   {'the end of the third quarter of fiscal 2022': 1}," +
        "   {'12-month': 1}," +
        "   {'quarter end': 1}," +
        "   {'annual': 1}," +
        "   {'2019': 2}," +
        "   {'the end of the third quarter': 1}," +
        "   {'six to 12 months': 1}," +
        "   {'the last 12 months ended September': 1}," +
        "   {'each quarter this year': 1}]," +
        "  'PERSON': [{'J.T. Rick': 1}," +
        "   {'Riles McMullen': 1}," +
        "   {'Steve Kinsey': 1}," +
        "   {'Riles': 4}," +
        "   {'JT': 1}," +
        "   {'Dave': 2}," +
        "   {'Killer Bread': 2}," +
        "   {'Canyon Bakehouse': 1}," +
        "   {'Debo Mukherjee': 1}," +
        "   {'Wonder Bread': 1}," +
        "   {'Smart': 1}," +
        "   {'Steve': 3}]," +
        "  'TIME': [{'8.30 a.m. Eastern': 1}]," +
        "  'CARDINAL': [{'10': 1}," +
        "   {'four': 1}," +
        "   {'one': 2}," +
        "   {'only one': 1}," +
        "   {'20 to 30 million': 1}," +
        "   {'310': 1}," +
        "   {'290': 1}," +
        "   {'200': 1}," +
        "   {'20 million': 1}," +
        "   {'34.6 million': 1}," +
        "   {'18.1 million': 1}," +
        "   {'24.4 million': 1}," +
        "   {'approximately 1.5': 1}," +
        "   {'100': 1}]," +
        "  'ORDINAL': [{'First': 1}," +
        "   {'second': 1}," +
        "   {'first': 2}," +
        "   {'third': 2}," +
        "   {'fourth': 1}]," +
        "  'NORP': [{'Flowers': 2}]," +
        "  'EVENT': [{'Hurricane Ian': 1}, {'Thanksgiving Day Parade': 1}]," +
        "  'WORK_OF_ART': [{\"Nature's Own\": 1}," +
        "   {\"Nature's Own Perfectly Crafted Sourdough\": 1}]," +
        "  'PERCENT': [{'13.2%': 1}," +
        "   {'11.1%': 1}," +
        "   {'18.5%': 1}," +
        "   {'12.7%': 1}," +
        "   {'17.8%': 1}," +
        "   {'5.1%': 1}," +
        "   {'46.8%': 1}," +
        "   {'38.6%': 1}," +
        "   {'36.4%': 1}," +
        "   {'11% to 12%': 1}," +
        "   {'10% to 12%': 1}," +
        "   {'9.9%': 1}," +
        "   {'1% to 2%': 1}," +
        "   {'7% to 9%': 1}," +
        "   {'11% and 20%': 1}]," +
        "  'MONEY': [{'\$25 to \$35 million': 1}," +
        "   {'\$11.6 million': 1}," +
        "   {'19 cents': 1}," +
        "   {'18 cents': 1}," +
        "   {'30 cents': 1}," +
        "   {'\$23.7 million to \$291.5 million': 1}," +
        "   {'\$41.6 million to \$128.4 million': 1}," +
        "   {'\$8.5 million to \$140.1 million': 1}," +
        "   {'approximately \$173 million': 1}," +
        "   {'approximately \$692 million': 1}," +
        "   {'approximately \$4.807 billion to \$4.850 billion': 1}," +
        "   {'\$1.25 to \$': 1}," +
        "   {'1.30': 1}]," +
        "  'LOC': [{'Outlook': 1}]," +
        "  'GPE': [{'US': 1}]}," +
        " 'Global_abstractive_summarization': \"Pre-recorded discussion of Flowers Foods third quarter 2022 results. Along with a transcript of these recorded remarks, you can find their earnings release and related slide presentation in investor section.Q&A to be held on Friday, November 11th at 8.30m. EasternGood day, ladies and gentlemen, and welcome to the call.Here is the full text of the third-quarter earnings release:Nature's Own grew sales dollars more than any other brand in fresh packaged bread. Dave's Killer and Canyon Bakehouse maintained unit share in tracked channelsI'm pleased to report that we are on track to meet or exceed our full-year targets.I'm proud of our team's response to the hurricaneDKB Epic Everything Breakfast Bread, Nature's Own Hawaiian Loaf, Canyon Bakehouse Brioche Rolls are among recent launches.At Flowers Foods, we're the first in the country to develop, package and ship food products.We're making progress on three of our strategic priorities: margins, recognition and recognition.Price increases were particularly impactful in non-branded products.We are revising our cost savings target from \$25 to \$35 million.At first, we were excited about the potential of digital. However, adoption was a bit slower than expectedAcquisition costs were \$11.6 million in the third quarter.I'm going to give an update on our progress in getting back to full health.I would like to take this opportunity to thank you for your continued support. Third quarter adjusted earnings per share rose 17.8%Cost of sales rose 5.7% on higher ingredient, packaging cost.Selling, distribution, and administrative expenses decreased 290 basis points.Third-quarter diluted EPS was 30 cents, unchanged from prior year period.Third-quarter net sales rose 7.5% to \$1.3 billion.Earnings per share to be in the range of \$1.30 to \$1.35. Sales guidance calls for an increase to bottom end of rangemidpoint of guidance exceeds long-term financial targets.I've been meaning to write this for a long time, but it's taken me so long that I don't know where to start.Here is the full text of the earnings call:Private label sales gained 100 basis points of unit share in the third quarter.We expect to benefit from cost advantages of eating in home compared to out of home.Good morning and thank you for joining us for this conference.\"," +
        " 'Global_text': \" Hello everyone and welcome to the pre-recorded discussion of Flowers Foods third quarter 2022 results. This is J.T. Rick, SVP of Finance and Investor Relations. We released earnings on November 10th, 2022. Along with a transcript of these recorded remarks, you can find their earnings release and related slide presentation in the investor section of flowersfoods.com. We will host a live Q&A session on Friday, November 11th at 8.30 a.m. Eastern. Further details are posted in the investor section of our website. Before we get started, keep in mind that the information presented here may include forward-looking statements about the company's performance. Although we believe these statements to be reasonable, they are subject to risks and uncertainties that could cause actual results to differ materially. In addition to what you hear in these remarks, important factors relating to Flowers Foods business are fully detailed in our SEC filings. Providing remarks today are Riles McMullen, President and CEO, and Steve Kinsey, our CFO. Riles, I'll turn it over to you. Thanks JT, it's a pleasure to welcome everyone to our call. We continue to execute well in the third quarter of 2022, driving quarterly sales to record levels. I'm particularly proud of our performance given the unique challenges presented by the current environment. Recessionary economic forces and inflation remain significant factors, and I'll discuss those topics in more detail a little bit later in the call. Rest assured that those near-term issues will not distract us from our focus on the tremendous longer-term opportunities in front of us. Despite widespread inflationary pressures, the strong performance of our leading brands continues to underscore consumers' preference for differentiated products. As measured by IRI, Nature's Own grew sales dollars more than any other brand in the fresh packaged bread category and added 10 basis points of unit share in tracked channels, the most out of all major brands. Our premium brands, Dave's Killer Bread and Canyon Bakehouse, both maintain unit share in the fresh packaged bread category as measured in tracked channels, even with double-digit price increases meant to offset inflation. Such a performance in an environment where the private label bread category gains share and track channels highlights the strength of our product portfolio and the resilience of demand. Supply chain pressures, which affected results in the first half of the year, were less impactful in the third quarter. Our procurement team is doing extraordinary work amid heightened volatility, securing additional sources of supply, and mitigating cost increases when possible. Now I'll address our four strategic priorities, which we expect to drive our results in 2022 and beyond. Developing our team, focusing on our brands, prioritizing margins, and pursuing smart M&A. First, as always, I'd like to thank our Flowers team for their hard work and dedication, which has made our strong performance possible. Many of our team members were impacted by the destruction of Hurricane Ian, though I'm relieved to report that all made it through safely. As they have done countless times in similar situations, our team stepped up to support those affected by the hurricane, offering supplies and increasing production at sister bakeries to meet consumers' heightened demand. I'm proud of our response, which demonstrates the passion of our team and their commitment to serving our customers. Our second strategic priority is focusing on our brands. Our accelerated growth and learn-improved portfolio roles are driving continued growth. Tracked channel dollar sales of our leading brands in the quarter were strong, with Nature's Own up 13.2%, Dave's Killer Bread up 11.1%, and Canyon Bakehouse up 18.5%. Our pipeline of new and innovative products is performing even better than expected. Recent launches are delivering promising results and include DKB Epic Everything Breakfast Bread, Nature's Own Hawaiian Loaf, Canyon Bakehouse Brioche Rolls, and Nature's Own Perfectly Crafted Sourdough. Looking ahead, it's critical that we develop new sources of revenue outside of our core business. We are making substantial investments in our Agile Innovation Group, which is leveraging the power of our number one brands to extend our reach beyond the traditional bread aisle. This team, led by our Chief Marketing Officer, Debo Mukherjee, is dedicated to commercializing innovative products using an asset light model that enables quicker responsiveness to consumer insights without significant upfront capital expenditures. DKB Snack Bars are the first products developed with this new capability, and their success in test markets has encouraged us to expand distribution nationally beginning in January 2023. We're also testing a line of high-protein DKB snack bars in select markets and have a robust pipeline of additional new products planned. We could not be more excited about this new innovation capability and the prospect it holds for driving future growth. If you'd like to sample these new products before they become widely available in stores, we've launched a direct-to-consumer website at creationsbyflowersfoods.com. This new channel allows us to test products directly with consumers and gather feedback ahead of formal product launches. Another exciting development for one of our brands is a new three-year partnership with Wonder Bread and the Macy's Thanksgiving Day Parade. This is the first time Wunder is participating in the parade, and we're capitalizing on that event with a series of promotions on social media and in-store displays. The programs have already generated enthusiastic response from media and consumers, and we expect the partnership to further strengthen Wunder's national brand recognition. Our third strategic priority is margins, which remain a particular focus given the inflationary environment. The price increases we began implementing in June have served to mitigate much of the impact of higher input costs. Although higher costs are dilutive to margin percentages, I would note that our adjusted EBITDA increased on a dollar basis in the quarter, helped by those price increases. Price increases were particularly impactful in non-branded products, where we were focused on improving profitability. As we've highlighted previously, enhancing the contributions of our balanced growth and maximized profitability portfolio roles is a key part of our margin enhancement strategy. But pricing is only one part of our plan to drive margin expansion. Other measures include our portfolio strategy, which aims to shift our mix to a greater portion of higher margin branded retail products, along with cost savings and operational efficiency programs. We continue to expect significant benefits from operational efficiencies and procurement in 2022. Though we are revising our cost savings target from \$25 to \$35 million. to 20 to 30 million. You may recall that a portion of these savings were planned to come from our digital efforts. We believe the investments we're making in digital will lead to meaningful efficiency improvements and be a key driver of improved margins. However, as our ERP implementation progressed, it became clear that we needed to dedicate additional internal resources to ensure its success. At the same time, adoption of these new digital technologies at some of our bakeries was a bit slower than expected, and continued operational inefficiencies somewhat hampered our efforts. So while we remain excited about the long-term potential of digital, some of the cost savings benefits we expected in the second half of 2022 will be pushed into 2023 as we focus our resources on ERP implementation and improved operational performance. Meanwhile, our ERP program remains on track, and we are confident in our ability to implement it as planned. Our fourth priority is Smart M&A. We continue to monitor the deal market, actively seeking potential acquisitions that add capabilities, brands, or products to our robust existing lineup. We believe our strong balance sheet positions us well to act when we have financial, commercial, and operational conviction. As part of that process, in the third quarter, we incurred \$11.6 million in costs from the pursuit of a potential acquisition that failed to materialize. Although we are disappointed in the result, we remain committed to our disciplined approach as we consider additional acquisition targets. Now I'll turn it over to Steve to review the details of the quarter. And then I'll come back a little bit later to discuss our outlook for the current business environment. Steve. Thank you, Riles, and hello, everyone. I'd like to echo your comments on our incredible team and express my sincere thanks for their outstanding efforts. As Riles mentioned, we are very pleased with our third quarter performance. Total sales increased 12.7% from the prior year period. Improved price mix drove the adjusted year-over-year increase up 17.8%, primarily due to price increases to mitigate inflationary pressures. Volume decreased 5.1%. mostly due to targeted sales rationalizations in cake and food service. Gross margin as a percentage of sales, excluding depreciation and amortization, decreased 310 basis points to 46.8%. Comparisons were impacted by higher ingredient and packaging cost. partly offset by higher sales that leveraged labor expenses and lower production volumes and outside purchases of product. Selling, distribution, and administrative expenses decreased 290 basis points as a percentage of sales to 38.6% in the third quarter. Results benefited from price increases that leveraged workforce-related expenses, lower employee fringe costs, and distributor distribution fees as a percent of sales. and decreased legal settlement and consulting costs, partly offset by acquisition-related costs. Excluding matters affecting comparability, adjusted SDNA expenses decreased 200 basis points to 36.4%. Gap diluted EPS for the quarter was 19 cents per share compared to 18 cents in the prior year period. Excluding the items affecting comparability detailed in the release, adjusted diluted EPS in the quarter was 30 cents per share, consistent with the prior year period. Turning now to our balance sheet liquidity and cash flow. Year to date, through the third quarter of fiscal 2022, cash flow from operating activities decreased by \$23.7 million to \$291.5 million. Capital expenditures increased \$41.6 million to \$128.4 million. largely due to the ongoing ERP upgrade, digital investments, and production capacity additions. Dividends paid increased \$8.5 million to \$140.1 million. In the second quarter, our Board of Directors increased the company's share repurchase authorization by 20 million shares. Year to date, we repurchased 34.6 million of common stock, including 18.1 million in the third quarter, leaving 24.4 million shares remaining for repurchase under the company's current share repurchase plan. Our financial position remains strong. At the end of the third quarter of fiscal 2022, net debt to trailing 12-month adjusted EBITDA stood at approximately 1.5 times. At quarter end, we held approximately \$173 million in cash and cash equivalents and had approximately \$692 million of remaining availability on our credit facilities. Now turning to our Outlook for 2022. Our updated sales guidance calls for an increase to the bottom end of the range. We now expect sales to be approximately \$4.807 billion to \$4.850 billion, representing an increase of 11% to 12% compared to prior guidance of 10% to 12%. Adjusted EPS is expected to be in the range of \$1.25 to \$1.30. implying a 9.9% compound annual growth rate off the 2019 base. The midpoint of this guidance exceeds our long-term financial targets of 1% to 2% sales growth. and 7% to 9% EPS growth off the 2019 base. At the end of the third quarter, virtually all our key commodities were covered for the remainder of 2022. To minimize volatility and provide adequate forewarning to allow for price adjustments, we have maintained our historical hedging strategy in which we attempt to increase the certainty of our key commodity costs six to 12 months out. Thank you, and now I'll turn it back to Riles. Thank you, Steve. You've just heard details on our strong financial results in the quarter. Now I'd like to address some of the key factors impacting the current environment, including inflationary pressures in the state of the US consumer. Inflation remains high, with the Consumer Price Index showing food and energy prices up 11% and 20% respectively over the last 12 months ended September. Those price increases are pressuring consumers and encouraging some to trade down to lower price products and shift more of their food purchases to value-oriented merchants such as mass, dollar, and club stores. As a result, in track channels measuring fresh packaged bread, overall private label sales gained 100 basis points of unit share in the third quarter. But as we noted on our last earnings call, that private label strength has been driven by concentrated activity in the mass merchandise channel where some private label retail prices have not adjusted to reflect inflationary pressures. In areas of retail where private label pricing generally reflects the higher cost, particularly in the grocery channel, brands are performing better. Our branded retail products gained unit share in tracked grocery channels for each quarter this year. At the same time, inflation is also pressuring the food service category. Consumers are eating more meals at home than prior to the pandemic, and that trend is expected to continue due to the cost advantages of eating in home compared to out of home. We expect to benefit from these trends as we focus our efforts on growing branded retail sales. Overall, demand elasticity has been in line with our expectations remaining below historical levels. The bread category has proven resilient over time, and that has been borne out even in the current environment. Our broad product lineup enables us to capture consumer demand at all price points and in a variety of distribution outlets. We are monitoring inflation and demand elasticity closely and remain confident that we will emerge from this period even stronger. We expect our relentless focus on developing new and innovative products for consumers and providing excellent service to our customers will continue to drive growth. The entire Flowers team remains committed to enhancing shareholder value, and we expect to continue to deliver results in line with or better than our long-term financial targets. Thank you very much for your time. This concludes our prepared remarks.\"," +
        " 'Global_sentiment': 'positive'}"